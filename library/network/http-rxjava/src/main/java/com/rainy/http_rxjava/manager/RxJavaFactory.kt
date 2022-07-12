package com.rainy.http_rxjava.manager

import com.rainy.http.factory.RetrofitFactory
import com.rainy.http_rxjava.api.RxJavaService
import retrofit2.CallAdapter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/3 00:24
 * @version: 1.0.0
 */
class RxJavaFactory : RetrofitFactory<RxJavaService>() {
    override fun getService(): RxJavaService = getService(RxJavaService::class.java)

    override fun getCallAdapterFactory(): CallAdapter.Factory = RxJava3CallAdapterFactory.create()
}