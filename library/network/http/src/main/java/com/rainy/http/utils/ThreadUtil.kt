@file:JvmName("ThreadUtil")

package com.rainy.http.utils


import android.os.Handler
import android.os.Looper

internal val handler = Handler(Looper.getMainLooper())

/** 判断是否是主线程 */
fun isMainThread(): Boolean {
    return Looper.getMainLooper() == Looper.myLooper()
}

/** 运行在主线程 */
fun runUIThread(action: () -> Unit) {
    handler.post {
        action.invoke()
    }
}

fun postDelayed(delay: Long, action: () -> Unit) {
    handler.postDelayed({ action.invoke() }, delay)
}