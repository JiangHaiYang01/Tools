package com.rainy.http.manager

import android.content.Context
import com.rainy.http.HttpPlugins
import com.rainy.http.config.HttpConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/7/3 00:03
 * @version: 1.0.0
 */
abstract class RetrofitManager<T> : IManager<T> {
    private lateinit var retrofit: Retrofit
    private lateinit var config: HttpConfig

    override fun onCreate(context: Context) {
        config = HttpPlugins.getConfig()
        val client = buildOkHttpClient(context)
        retrofit = createRetrofit(client)
    }

    private fun createRetrofit(okHttpBuilder: OkHttpClient.Builder): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
        val client = retrofitBuilder.client(okHttpBuilder.build())
        return client.baseUrl(config.baseUrl).build()
    }

    /**
     * 创建OkHttpClient
     * @return OkHttpClient.Builder
     */
    private fun buildOkHttpClient(context: Context): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        // 第三方库 管理 cookie
//        okHttpBuilder.cookieJar(
//            PersistentCookieJar(
//                SetCookieCache(),
//                SharedPrefsCookiePersistor(context)
//            )
//        )
        // 读写超时
        okHttpBuilder.connectTimeout(config.connectTime.time, config.connectTime.timeUnit)
        okHttpBuilder.readTimeout(config.readTime.time, config.readTime.timeUnit)
        okHttpBuilder.writeTimeout(config.writeTime.time, config.writeTime.timeUnit)
//        // 动态替换BaseUrl
//        okHttpBuilder.addInterceptor(ReplaceBaseUrlInterceptor())
//        // 动态替换连接超时
//        okHttpBuilder.addInterceptor(ReplaceTimeInterceptor())
//        // 添加日志拦截器
//        okHttpBuilder.addNetworkInterceptor(LoggingInterceptor.register())
//        // 添加公共请求头
//        okHttpBuilder.addInterceptor(BasicHeardInterceptor.register(HttpPlugins.getHeard()))
        return okHttpBuilder
    }

    /**
     * 获取 Retrofit 请求接口
     * @return T
     */
    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }
}