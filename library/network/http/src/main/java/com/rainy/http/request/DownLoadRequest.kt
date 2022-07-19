package com.rainy.http.request

import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.rainy.http.HttpManager
import com.rainy.http.utils.DownRequest
import com.rainy.http.utils.inlinePrintLog

private const val TAG = "DownLoadRequest"

fun Request.asDownLoad(
    owner: LifecycleOwner? = null,
    dest: String,
    name: String,
    tag: String? = null,
    bufferSize: Long = 1024L
) {
    val workRequest = createRequest(
        DownRequest(
            tag = tag,
            url = path,
            destPath = dest,
            name = name,
            bufferSize = bufferSize
        )
    )
    WorkManager.getInstance(HttpManager.context).enqueue(workRequest)
    val infoLiveData = WorkManager.getInstance(HttpManager.context)
        .getWorkInfoByIdLiveData(workRequest.id)
    if (owner == null) {
        infoLiveData.observeForever { info -> observerLiveData(info) }
    } else {
        infoLiveData.observe(owner) { info -> observerLiveData(info) }
    }
}

private fun observerLiveData(workInfo: WorkInfo) {
    val progress = workInfo.progress.getInt(DownLoadWorker.KEY_PROGRESS, 0)
    val currentSize = workInfo.progress.getLong(DownLoadWorker.KEY_CURRENT_SIZE, 0L)
    val totalSize = workInfo.progress.getLong(DownLoadWorker.KEY_TOTAL_SIZE, 0L)
    inlinePrintLog(
        TAG,
        "total:$totalSize,current:$currentSize,progress:$progress in ${Thread.currentThread().name}"
    )
}

private fun createRequest(request: DownRequest): WorkRequest {
    val builder = OneTimeWorkRequestBuilder<DownLoadWorker>()
    request.tag?.let { tag ->
        builder.addTag(tag)
    }
    return builder.setInputData(createInputData(request)).build()
}

private fun createInputData(request: DownRequest): Data {
    return Data.Builder()
        .putString(DownLoadWorker.URL, request.url)
        .putString(DownLoadWorker.DEST, request.destPath)
        .putString(DownLoadWorker.NAME, request.name)
        .build()
}