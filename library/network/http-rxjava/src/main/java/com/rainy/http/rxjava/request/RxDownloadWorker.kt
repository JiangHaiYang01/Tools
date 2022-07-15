package com.rainy.http.rxjava.request

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import okio.*
import java.io.File
import kotlin.io.use

class RxDownloadWorker(
    context: Context,
    params: WorkerParameters
) : RxWorker(context, params) {
    override fun createWork(): Single<Result> {
        return getRxjavaService().download(
            "",
            "https://c-ssl.dtstatic.com/uploads/blog/202104/24/20210424092725_d1de2.thumb.1000_0.jpeg"
        )
            .map { body ->
                File("").use { sink ->
//                    body.use(sink) {
//
//                    }
                }

                Result.success()
            }.onErrorReturn {
                Result.failure()
            }
    }
}

private fun File.use(action: (BufferedSink) -> Unit) {
    File(path).sink().buffer().use {
        action(it)
    }
}

private fun ResponseBody.use(sink: BufferedSink, bufferSize: Long = 1024L) {
    this.byteStream().source().use { source ->
//        while (source.read(sink.buffer, bufferSize).also { len = it } != -1L) {
//
//        }
    }
}