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

    fun delete(path: String): Request = Request(RequestMode.DELETE, path)

    fun put(path: String): Request = Request(RequestMode.PUT, path)

    fun form(path: String): Request = Request(RequestMode.FORM, path)
}
