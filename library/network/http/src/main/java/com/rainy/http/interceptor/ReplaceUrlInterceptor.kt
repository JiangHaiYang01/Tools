package com.rainy.http.interceptor

import com.rainy.http.HttpManager
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URL

/**
 * 主要功能: 动态替换BaseUrl
 * @Description:
 * @author : jhy
 * @date: 2021年08月13日 9:14 下午
 * @version: 1.0.0
 */
class ReplaceUrlInterceptor : Interceptor {

    companion object {
        private const val HTTPS = "https://"
        private const val HTTP = "http://"
        private const val HTTPS_SCHEME = "https"
        private const val HTTP_SCHEME = "http"
        const val DYNAMIC_BASE_URL = "com.rainy.http.dynamic.base.url"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val list = request.headers.filter {
            it.first == DYNAMIC_BASE_URL
        }
        if (list.isEmpty()) {
            return chain.proceed(request)
        }
        val builder = request.url.newBuilder()
        kotlin.runCatching {
            scheme(list.first().second, builder)
            host(list.first().second, builder)
        }.onFailure {
            HttpManager.errorAction?.invoke(it)
        }
        val newRequest = request.newBuilder()
            .removeHeader(DYNAMIC_BASE_URL)
            .url(builder.build())
            .build()
        return chain.proceed(newRequest)
    }

    private fun host(
        newUrl: String,
        builder: HttpUrl.Builder,
    ) {
        builder.host(URL(newUrl).host)
    }

    private fun scheme(
        newUrl: String,
        builder: HttpUrl.Builder,
    ) {
        when {
            newUrl.startsWith(HTTPS) -> builder.scheme(HTTPS_SCHEME)
            newUrl.startsWith(HTTP) -> builder.scheme(HTTP_SCHEME)
            else -> builder.scheme(HTTPS_SCHEME)
        }
    }
}