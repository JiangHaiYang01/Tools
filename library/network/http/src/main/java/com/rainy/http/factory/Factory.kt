package com.rainy.http.factory

import android.content.Context

/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/3 00:03
 * @version: 1.0.0
 */
abstract class Factory {
    abstract fun onCreate(context: Context)
}