package com.rainy.http.rxjava.request

import android.content.Context
import androidx.work.WorkerParameters
import com.rainy.http.request.DownLoadWorker
import com.rainy.http.utils.DownRequest
import okhttp3.ResponseBody

class RxDownloadWorker(
    context: Context,
    params: WorkerParameters
) : DownLoadWorker(context, params) {

    override fun getResponseBody(downRequest: DownRequest): ResponseBody {
        return getRxjavaService().download("", url = downRequest.url).blockingGet()
    }
}