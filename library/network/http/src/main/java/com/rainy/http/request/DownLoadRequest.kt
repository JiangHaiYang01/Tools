package com.rainy.http.request

import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.rainy.http.HttpManager
import com.rainy.http.interceptor.LogInterceptor
import com.rainy.http.response.*
import com.rainy.http.utils.DownRequest
import okhttp3.logging.HttpLoggingInterceptor

private const val TAG = "DownLoadRequest"

fun <T : ListenableWorker> Request.doDownLoad(
    owner: LifecycleOwner? = null,
    dest: String,
    name: String,
    tag: String? = null,
    bufferSize: Long = 1024L,
    clazz: Class<T>
): DownLoadResponse {
    // 下载不需要日志
    LogInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
    val workRequest = createRequest(
        DownRequest(
            tag = tag,
            url = path,
            destPath = dest,
            name = name,
            bufferSize = bufferSize,
        ),
        clazz
    )
    val response = DownLoadResponse(workRequest.id)
    WorkManager.getInstance(HttpManager.context).enqueue(workRequest)
    val infoLiveData = WorkManager.getInstance(HttpManager.context)
        .getWorkInfoByIdLiveData(workRequest.id)
    if (owner == null) {
        infoLiveData.observeForever { info -> observerLiveData(info, response, dest, name) }
    } else {
        infoLiveData.observe(owner) { info -> observerLiveData(info, response, dest, name) }
    }
    return response
}

private fun observerLiveData(
    workInfo: WorkInfo,
    response: DownLoadResponse,
    dest: String,
    name: String,
) {
    when (workInfo.state) {
        WorkInfo.State.SUCCEEDED -> {
            success(response, dest, name)
        }
        WorkInfo.State.RUNNING -> {
            progress(workInfo, response)
        }
        WorkInfo.State.FAILED -> {
            failed(response)
        }
        WorkInfo.State.CANCELLED -> {
            cancel(workInfo, response)
        }
        else -> {

        }
    }
}

private fun failed(response: DownLoadResponse) {
    response.actionFailed?.invoke(ErrorResult(response.id, Throwable("work manager failed")))
}

private fun cancel(workInfo: WorkInfo, response: DownLoadResponse) {
    response.actionCancel?.invoke(workInfo.id.toString())
}

private fun progress(workInfo: WorkInfo, response: DownLoadResponse) {
    val progress = workInfo.progress.getInt(DownLoadWorker.KEY_PROGRESS, 0)
    val currentSize = workInfo.progress.getLong(DownLoadWorker.KEY_CURRENT_SIZE, 0L)
    val totalSize = workInfo.progress.getLong(DownLoadWorker.KEY_TOTAL_SIZE, 0L)
    response.actionProgress?.invoke(
        ProgressResult(
            response.id,
            progress,
            currentSize,
            totalSize
        )
    )
}

private fun success(
    response: DownLoadResponse,
    dest: String,
    name: String,
) {
    response.actionSuccess?.invoke(
        SuccessResult(
            response.id,
            "$dest/$name"
        )
    )
}

private fun <T : ListenableWorker> createRequest(
    request: DownRequest,
    clazz: Class<T>
): WorkRequest {
    val builder = OneTimeWorkRequest.Builder(clazz)
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