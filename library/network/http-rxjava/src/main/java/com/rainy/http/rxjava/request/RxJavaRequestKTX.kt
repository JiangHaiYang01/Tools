package com.rainy.http.rxjava.request

import androidx.lifecycle.LifecycleOwner
import androidx.work.ListenableWorker
import com.rainy.http.HttpManager
import com.rainy.http.ktx.asClazz
import com.rainy.http.request.Request
import com.rainy.http.request.RequestMode
import com.rainy.http.request.doDownLoad
import com.rainy.http.response.DownLoadResponse
import com.rainy.http.rxjava.api.RxJavaService
import com.rainy.http.rxjava.manager.RxJavaFactory
import com.rainy.http.utils.*
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

fun Request.asDownLoad(
    owner: LifecycleOwner? = null,
    dest: String,
    name: String,
    tag: String? = null,
    bufferSize: Long = 1024L
): DownLoadResponse = doDownLoad(owner, dest, name, tag, bufferSize, RxDownloadWorker::class.java)

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