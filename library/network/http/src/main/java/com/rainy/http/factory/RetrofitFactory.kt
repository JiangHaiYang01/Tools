package com.rainy.http.factory

import android.content.Context
import com.rainy.http.HttpManager
import com.rainy.http.config.Config
import com.rainy.http.interceptor.BasicHeardInterceptor
import com.rainy.http.interceptor.LogInterceptor
import com.rainy.http.interceptor.ReplaceUrlInterceptor
import com.rainy.http.utils.inlineError
import com.rainy.http.utils.inlinePrintLog
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit

/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/3 00:03
 * @version: 1.0.0
 */
abstract class RetrofitFactory<T> : Factory() {
    private lateinit var retrofit: Retrofit
    private lateinit var config: Config

    companion object {
        private const val TAG = "RetrofitFactory"
    }

    override fun onCreate(context: Context) {
        inlinePrintLog(TAG, "create retrofit factory")
        kotlin.runCatching {
            config = HttpManager.config
            val client = buildOkHttpClient()
            retrofit = createRetrofit(client)
        }.onFailure {
            inlineError(it)
        }
    }

    private fun createRetrofit(okHttpBuilder: OkHttpClient.Builder): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
        val client = retrofitBuilder.client(okHttpBuilder.build())
        retrofitBuilder.addCallAdapterFactory(getCallAdapterFactory())
        return client.baseUrl(config.baseUrl).build()
    }

    /**
     * 创建OkHttpClient
     * @return OkHttpClient.Builder
     */
    private fun buildOkHttpClient(): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        // 读写超时
        okHttpBuilder.connectTimeout(config.connectTime.time, config.connectTime.timeUnit)
        okHttpBuilder.readTimeout(config.readTime.time, config.readTime.timeUnit)
        okHttpBuilder.writeTimeout(config.writeTime.time, config.writeTime.timeUnit)

        // 添加日志拦截器
        okHttpBuilder.addNetworkInterceptor(LogInterceptor.register(HttpManager.level))
        // 添加公共请求头
        okHttpBuilder.addInterceptor(BasicHeardInterceptor.register(config.heard))
        // 动态替换BaseUrl
        okHttpBuilder.addInterceptor(ReplaceUrlInterceptor())
//        // 动态替换连接超时
//        okHttpBuilder.addInterceptor(ReplaceTimeInterceptor())

        return okHttpBuilder
    }

    /**
     * 获取 Retrofit 请求接口
     * @return T
     */
    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }

    abstract fun getService(): T

    fun getRetrofit(): Retrofit = retrofit

    abstract fun getCallAdapterFactory(): CallAdapter.Factory
}