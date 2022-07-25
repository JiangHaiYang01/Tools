package com.rainy.http.interceptor

import com.rainy.http.HttpManager
import com.rainy.http.utils.inlinePrintLog
import okhttp3.logging.HttpLoggingInterceptor

/**
 * 主要功能: 网络请求日志拦截器
 * @Description:
 * @author : jhy
 * @date: 2021年08月13日 9:58 下午
 * @version: 1.0.0
 */
object LogInterceptor {
    private const val TAG = "LogInterceptor"

    private lateinit var mInterceptor: HttpLoggingInterceptor

    fun register(level: HttpLoggingInterceptor.Level): HttpLoggingInterceptor {
        mInterceptor = HttpLoggingInterceptor { info ->
            if (HttpManager.debug) {
                inlinePrintLog(TAG, info)
            }
            HttpManager.logList.forEach {
                it.invoke(info)
            }
        }.apply {
            setLevel(level)
        }
        return mInterceptor
    }

    fun setLevel(level: HttpLoggingInterceptor.Level) {
        mInterceptor.setLevel(level)
    }
}

