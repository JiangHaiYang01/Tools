package com.rainy.http.response

import java.util.*

class DownLoadResponse(val id: UUID) {
    // 下载进度
    internal var actionProgress: ((ProgressResult) -> Unit)? = null

    // 下载成功
    internal var actionSuccess: ((SuccessResult) -> Unit)? = null

    // 下载失败
    internal var actionFailed: ((ErrorResult) -> Unit)? = null

    // 下载取消
    internal var actionCancel: ((String) -> Unit)? = null

    // 下载暂停
    internal var actionPause: ((String) -> Unit)? = null

    fun onProgress(action: (ProgressResult) -> Unit) = apply { actionProgress = action }

    fun onSuccess(action: (SuccessResult) -> Unit) = apply { actionSuccess = action }

    fun onFailure(action: (ErrorResult) -> Unit) = apply { actionFailed = action }

    fun onCancel(action: (String) -> Unit) = apply { actionCancel = action }

    fun onPause(action: (String) -> Unit) = apply { actionPause = action }
}

data class ErrorResult(
    var id: UUID,
    val throwable: Throwable
)

data class SuccessResult(
    /**
     * key
     */
    var id: UUID,

    /**
     * 下载位置
     */
    val path: String
)

data class ProgressResult(

    /**
     * key
     */
    var id: UUID,

    /**
     * 当前进度 0-100
     */
    var progress: Int = 0,

    /**
     * 当前已完成的字节大小
     */
    var currentSize: Long = 0,

    /**
     * 总字节大小
     */
    var totalSize: Long = 0,
)