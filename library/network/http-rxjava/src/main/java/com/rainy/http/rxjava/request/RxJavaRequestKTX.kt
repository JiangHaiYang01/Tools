package com.rainy.http.rxjava.request

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.rainy.http.HttpManager
import com.rainy.http.ktx.asClazz
import com.rainy.http.request.Request
import com.rainy.http.request.RequestMode
import com.rainy.http.rxjava.api.RxJavaService
import com.rainy.http.rxjava.manager.RxJavaFactory
import com.rainy.http.utils.DownRequest
import com.rainy.http.utils.asRequestBody
import com.rainy.http.utils.inlineError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle

/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/11 00:46
 * @version: 1.0.0
 */

inline fun <reified T : Any> Request.asResponse(owner: LifecycleOwner? = null): Single<T> {
    return runRxJavaCatching {
        execute(getRxjavaService()).aWait().bindUntilEvent(owner).map { it.asClazz<T>() }
    }
}

fun Request.asDownLoad(owner: LifecycleOwner) {
    val uploadWorkRequest = createRequest(path)
    WorkManager.getInstance().enqueue(uploadWorkRequest)

    WorkManager.getInstance()
        .getWorkInfoByIdLiveData(uploadWorkRequest.id)
        .observe(owner) {
            Log.d("outputData", "----------->$it")
            it.progress.getString("progress")
        }
}

private fun createRequest(request: DownRequest): WorkRequest {
    return OneTimeWorkRequestBuilder<RxDownloadWorker>()
        .setInputData(createInputData(request))
        .build()
}

private fun createInputData(request: DownRequest): Data {
    return Data.Builder()
        .putString(RxDownloadWorker.URL, request.url)
        .putString(RxDownloadWorker.DEST, request.destPath)
//        .putString(RxDownloadWorker.NAME, request.name)
        .build()
}


fun getRxjavaService(): RxJavaService {
    val factory = HttpManager.factory
    if (factory !is RxJavaFactory) {
        throw Throwable("this is not RxJava Factory")
    }
    return factory.getService()
}

fun <T : Any> runRxJavaCatching(action: () -> Single<T>): Single<T> {
    return try {
        action.invoke()
    } catch (error: Throwable) {
        inlineError(error)
        Single.error(error)
    }
}

fun Request.execute(rxJavaService: RxJavaService): Single<ResponseBody> {
    return when (type) {
        RequestMode.GET -> rxJavaService.doGet(headers = head, url = path)
        RequestMode.FORM -> rxJavaService.doPost(headers = head, params = params, path = path)
        RequestMode.PUT -> rxJavaService.doPut(headers = head, maps = params, path = path)
        RequestMode.DELETE -> rxJavaService.doDelete(headers = head, maps = params, path = path)
        RequestMode.POST -> rxJavaService.doBody(
            headers = head,
            body = params.asRequestBody(),
            path = path
        )
        else -> throw Throwable("invalid type")
    }
}

inline fun <reified T : Any> Single<T>.aWait(): Single<T> {
    return subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T : Any> Single<T>.bindUntilEvent(owner: LifecycleOwner?): Single<T> {
    return if (owner == null) {
        this
    } else {
        bindToLifecycle(owner)
    }
}