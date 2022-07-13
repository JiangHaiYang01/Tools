package com.rainy.http.interceptor

import okhttp3.Interceptor
import okhttp3.Request

/**
 * 主要功能: 为所有请求添加请求头
 * @Description:
 * @author : jhy
 * @date: 2021年08月13日 10:07 下午
 * @version: 1.0.0
 */
object BasicHeardInterceptor {
    fun register(map: Map<String, String>): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            map.forEach {
                builder.addHeader(it.key, it.value)
            }
            chain.proceed(builder.build())
        }
    }
}