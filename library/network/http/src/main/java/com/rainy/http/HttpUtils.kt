package com.rainy.http

import com.rainy.http.request.Request
import com.rainy.http.request.RequestMode


/**
 * @author jhy
 * @description http
 * @date 2021/10/21
 */
object HttpUtils {

    fun get(path: String): Request = Request(RequestMode.GET, path)

    fun post(path: String): Request = Request(RequestMode.POST, path)
}
