package com.rainy.http_rxjava.manager

import com.rainy.http.manager.RetrofitManager
import com.rainy.http_rxjava.api.RxJavaService
import com.rainy.http_rxjava.asResponse

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/7/3 00:24
 * @version: 1.0.0
 */
class RxManager : RetrofitManager<RxJavaService>() {
    override fun getManager(): RxJavaService {
        return getService(RxJavaService::class.java)
    }
}

fun main() {
//    RxManager().getManager()
//        .doGet()
//        .asResponse()
//        .subscribe { body, error ->
//
//        }
}