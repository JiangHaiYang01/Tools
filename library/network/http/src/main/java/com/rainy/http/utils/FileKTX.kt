package com.rainy.http.utils

import okio.Buffer
import okio.Source
import okio.buffer
import okio.sink
import java.io.File

private const val TAG = "FileKTX"

fun File.save(
    // 资源
    source: Source,
    // 资源长度
    total: Long,
    // 一次读取
    bufferSize: Long = 1024L,
    // cancel
    cancel: () -> Boolean,
    // 进度
    progress: ((ProgressData) -> Unit)? = null
) {
    File(path).sink().buffer().use { sink ->
        if (isMainThread()) {
            throw Throwable("io can not in main thread")
        }
        val buffer: Buffer = sink.buffer
        source.use { source ->
            var len: Long
            var current: Long = 0
            var mLastProgress = 0
            var temProgress: ProgressData? = null
            while (source.read(buffer, bufferSize).also { len = it } != -1L) {
                if (cancel.invoke()) {
                    inlinePrintLog(TAG, "cancel save")
                    break
                }
                current += len
                val tem = (current * 100 / total).toInt()
                if (tem != mLastProgress) {
                    mLastProgress = tem
                    if (temProgress == null) {
                        temProgress = ProgressData(
                            progress = mLastProgress,
                            currentSize = current,
                            totalSize = total
                        )
                    } else {
                        temProgress.currentSize = current
                        temProgress.progress = mLastProgress
                        temProgress.totalSize = total
                    }
                    temProgress.also {
                        runUIThread {
                            progress?.invoke(it)
                        }
                    }
                }
            }
        }
    }
}
