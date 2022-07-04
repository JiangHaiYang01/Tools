package com.rainy.http.call

import com.rainy.http.request.RequestParams

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/7/2 23:52
 * @version: 1.0.0
 */
interface Call {

    fun enqueue(params: RequestParams)

    fun execute(params: RequestParams)

}