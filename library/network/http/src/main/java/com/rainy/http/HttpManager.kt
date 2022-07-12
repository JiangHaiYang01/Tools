package com.rainy.http

import com.google.gson.Gson
import com.rainy.http.config.HttpConfig
import com.rainy.http.factory.Factory
import okhttp3.logging.HttpLoggingInterceptor

object HttpManager {
    var factory: Factory? = null

    var errorAction: ((Throwable) -> Unit)? = null

    var gson = Gson()

    val config: HttpConfig = HttpConfig()

    var debug = true

    var level = HttpLoggingInterceptor.Level.BODY

}