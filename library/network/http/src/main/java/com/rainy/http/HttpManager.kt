package com.rainy.http

import com.google.gson.Gson
import com.rainy.http.config.Config
import com.rainy.http.factory.Factory
import okhttp3.logging.HttpLoggingInterceptor

object HttpManager {
    val gson = Gson()

    val config: Config = Config()

    val logList = ArrayList<(String) -> Unit>()

    var factory: Factory? = null

    var errorAction: ((Throwable) -> Unit)? = null

    var debug = true

    var level = HttpLoggingInterceptor.Level.BODY
}