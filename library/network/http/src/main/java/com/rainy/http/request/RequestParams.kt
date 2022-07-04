package com.rainy.http.request

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/7/2 21:42
 * @version: 1.0.0
 */
data class RequestParams(
    @RequestMode val type: Int,
    val head: HashMap<String, String>,
    val params: HashMap<String, Any>,
    val path: String
)