package com.rainy.http_rxjava.request

import com.rainy.http.HttpManager
import com.rainy.http.ktx.asClazz
import com.rainy.http.request.Request
import com.rainy.http.request.RequestMode
import com.rainy.http_rxjava.api.RxJavaService
import com.rainy.http_rxjava.manager.RxJavaFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/11 00:46
 * @version: 1.0.0
 */

inline fun <reified T : Any> Request.asResponse(): Single<T> {
    return runRxJavaCatching {
        val factory = HttpManager.factory
        if (factory !is RxJavaFactory) {
            throw Throwable("this is not RxJava Factory")
        }
        val rxJavaService = factory.getService()
        execute(rxJavaService).aWait().map { it.asClazz<T>() }
    }
}

fun <T : Any> runRxJavaCatching(action: () -> Single<T>): Single<T> {
    return try {
        action.invoke()
    } catch (error: Throwable) {
        HttpManager.errorAction?.invoke(error)
        Single.error(error)
    }
}

fun Request.execute(rxJavaService: RxJavaService): Single<ResponseBody> {
    return when (type) {
        RequestMode.GET -> rxJavaService.doGet(headers = head, url = path)
        RequestMode.POST -> rxJavaService.doPost(headers = head, params = params, path = path)
        else -> throw Throwable("invalid type")
    }
}

inline fun <reified T : Any> Single<T>.aWait(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}