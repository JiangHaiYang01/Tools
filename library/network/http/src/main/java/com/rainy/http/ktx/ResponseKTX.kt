package com.rainy.http.ktx

import com.rainy.http.HttpManager
import okhttp3.ResponseBody

/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/11 00:58
 * @version: 1.0.0
 */

inline fun <reified T> ResponseBody.asClazz(): T {
    return if (T::class.java.name == String::class.java.name) {
        string() as T
    } else {
        HttpManager.gson.fromJson(string(), T::class.java)
    }
}

