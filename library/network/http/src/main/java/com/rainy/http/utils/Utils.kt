package com.rainy.http.utils

import android.util.Log
import com.rainy.http.HttpManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun inlinePrintLog(tag: String, msg: String) {
    if (HttpManager.debug)
        Log.i(tag, msg)
}

fun inlineError(throwable: Throwable) {
    HttpManager.errorAction?.invoke(throwable)
}

fun HashMap<String, Any>.asRequestBody(): RequestBody =
    HttpManager.gson.toJson(this).toRequestBody("application/json".toMediaTypeOrNull())