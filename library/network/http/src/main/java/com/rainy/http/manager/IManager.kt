package com.rainy.http.manager

import android.content.Context

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/7/3 00:03
 * @version: 1.0.0
 */
interface IManager<T> {
    fun onCreate(context: Context)

    fun getManager(): T
}