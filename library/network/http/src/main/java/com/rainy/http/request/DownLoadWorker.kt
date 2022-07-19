package com.rainy.http.request

import android.content.Context
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.rainy.http.utils.DownRequest
import com.rainy.http.utils.inlinePrintLog
import com.rainy.http.utils.save2File
import okhttp3.ResponseBody

abstract class DownLoadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    companion object {
        private const val TAG = "DownloadWorker"

        const val URL = "com.rainy.http.rxjava.download.url"
        const val DEST = "com.rainy.http.rxjava.download.dest"
        const val NAME = "com.rainy.http.rxjava.download.name"

        const val KEY_PROGRESS = "com.rainy.http.rxjava.download.progress"
        const val KEY_CURRENT_SIZE = "com.rainy.http.rxjava.download.currentSize"
        const val KEY_TOTAL_SIZE = "com.rainy.http.rxjava.download.totalSize"
    }

    abstract fun getResponseBody(downRequest: DownRequest): ResponseBody

    override fun doWork(): Result {
        try {
            val url = getUrl()
            val destPath = getDestPath()
            val name = getFileName()
            val downRequest = DownRequest(destPath, name = name, url = url)
            inlinePrintLog(TAG, "downRequest:$downRequest")
            val responseBody = getResponseBody(downRequest)
            responseBody.save2File(downRequest) { progress ->
                val data = workDataOf(
                    KEY_PROGRESS to progress.progress,
                    KEY_CURRENT_SIZE to progress.currentSize,
                    KEY_TOTAL_SIZE to progress.totalSize,
                )
                setProgressAsync(data)
            }
            return Result.success()
        } catch (throwable: Throwable) {
            return Result.failure()
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

    private fun getFileName(): String {
        val name = inputData.getString(NAME)
        if (name == null || TextUtils.isEmpty(name)) {
            throw Throwable("download file name is empty or null")
        }
        return name
    }
}
