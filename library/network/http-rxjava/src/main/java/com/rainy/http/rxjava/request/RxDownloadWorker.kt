package com.rainy.http.rxjava.request

import android.content.Context
import android.text.TextUtils
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import androidx.work.workDataOf
import com.rainy.http.utils.DownRequest
import com.rainy.http.utils.inlinePrintLog
import com.rainy.http.utils.save2File
import io.reactivex.rxjava3.core.Single

class RxDownloadWorker(
    context: Context,
    params: WorkerParameters
) : RxWorker(context, params) {
    companion object {
        private const val TAG = "RxDownloadWorker"

        const val URL = "com.rainy.http.rxjava.download.url"
        const val DEST = "com.rainy.http.rxjava.download.dest"

        const val KEY_PROGRESS = "com.rainy.http.rxjava.download.progress"
        const val KEY_CURRENT_SIZE = "com.rainy.http.rxjava.download.currentSize"
        const val KEY_TOTAL_SIZE = "com.rainy.http.rxjava.download.totalSize"
    }

    override fun createWork(): Single<Result> {
        val url = getUrl()
        val destPath = getDestPath()
        return getRxjavaService().download("", url = url).map { body ->
            val downRequest = DownRequest(destPath, url = url)
            inlinePrintLog(TAG, "downRequest:$downRequest")
            body.save2File(downRequest) { progress ->
                val data = workDataOf(
                    KEY_PROGRESS to progress.progress,
                    KEY_CURRENT_SIZE to progress.currentSize,
                    KEY_TOTAL_SIZE to progress.totalSize,
                )
                setProgressAsync(data)
            }
            Result.success()
        }.onErrorReturn {
            inlinePrintLog(TAG, "failed:${it.message}")
            Result.failure()
        }
    }

    private fun getUrl(): String {
        val url = inputData.getString(URL)
        if (url == null || TextUtils.isEmpty(url)) {
            throw Throwable("download url is empty or null")
        }
        return url
    }

    private fun getDestPath(): String {
        val dest = inputData.getString(DEST)
        if (dest == null || TextUtils.isEmpty(dest)) {
            throw Throwable("download dest path name is empty or null")
        }
        return dest
    }
}