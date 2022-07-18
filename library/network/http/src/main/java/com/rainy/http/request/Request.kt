package com.rainy.http.request

import android.text.TextUtils
import com.rainy.http.HttpManager
import com.rainy.http.interceptor.ReplaceUrlInterceptor
import com.rainy.http.utils.inlinePrintLog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 *
 * @Description:
 * @author : rainy
 * @date: 2022/7/2 21:42
 * @version: 1.0.0
 */
open class Request constructor(@RequestMode val type: Int, val path: String) {
    companion object {
        const val TAG = "Request"
    }

    val head = HashMap<String, String>()
    val params = HashMap<String, Any>()
}

/**
 * 添加请求头
 */
fun Request.addHeard(key: String, value: String) = apply {
    head[key] = value
}

/**
 * 添加参数
 */
fun Request.addParam(key: String, value: String) = apply {
    params[key] = value
}

/**
 * 动态切换baseUrl
 */
fun Request.setUrl(url: String) = apply {
    if (TextUtils.isEmpty(url)) {
        inlinePrintLog(Request.TAG, "new base url is invalid")
        return@apply
    }
    addHeard(ReplaceUrlInterceptor.DYNAMIC_BASE_URL, url)
}