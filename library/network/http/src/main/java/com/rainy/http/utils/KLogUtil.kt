package com.rainy.http.utils

import android.util.Log
import com.rainy.http.HttpManager

fun printLog(tag: String, msg: String) {
    if (HttpManager.debug)
        Log.i(tag, msg)
}