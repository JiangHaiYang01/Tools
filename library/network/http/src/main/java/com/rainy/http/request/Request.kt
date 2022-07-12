package com.rainy.http.request

/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/2 21:42
 * @version: 1.0.0
 */
class Request constructor(@RequestMode val type: Int, val path: String) {
    val head = HashMap<String, String>()
    val params = HashMap<String, Any>()
}

fun Request.addHeard(key: String, value: String) = apply {
    head[key] = value
}

fun Request.addParam(key: String, value: String) = apply {
    params[key] = value
}