package com.rainy.http

import com.rainy.http.config.HttpConfig

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/7/3 00:31
 * @version: 1.0.0
 */
object HttpPlugins {

    private val config: HttpConfig = HttpConfig()

    fun setBaseUrl(url: String) = apply { this.config.baseUrl = url }

    fun getConfig(): HttpConfig = config

    fun init() {

    }
}