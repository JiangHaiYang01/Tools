package com.rainy.http.utils

import androidx.annotation.WorkerThread
import okhttp3.ResponseBody
import java.io.File

private const val TAG = "ResponseBodyKTX"

/** body 保存到文件 */
@WorkerThread
fun ResponseBody.save2File(
    // request
    request: DownRequest,
    // cancel
    cancel: () -> Boolean = { false },
    // 下载进度
    actionProgress: ((ProgressData) -> Unit)? = null,
) {
    var temProgress: ProgressData? = null
    val file = File(request.destPath)
    if (!file.exists()) {
        inlinePrintLog(TAG, "create file path:${file.path}")
        file.mkdirs()
    }
    File(request.destPath).save(
        source = this.source(),
        total = this.contentLength(),
        bufferSize = request.bufferSize,
        cancel = cancel
    ) {
        if (actionProgress == null) return@save
        if (temProgress == null) {
            temProgress = ProgressData(
                progress = it.progress,
                currentSize = it.currentSize,
                totalSize = it.totalSize,
            )
        } else {
            temProgress.let { tem ->
                tem?.progress = it.progress
                tem?.currentSize = it.currentSize
                tem?.totalSize = it.totalSize
            }
        }
        temProgress?.also { tem ->
            actionProgress.invoke(tem)
        }
    }
}

data class ProgressData(

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

data class DownRequest(
    // 下载文件的位置
    val destPath: String,

    // buffer 大小
    val bufferSize: Long = 1024L,

    // 下载地址
    val url: String,
)