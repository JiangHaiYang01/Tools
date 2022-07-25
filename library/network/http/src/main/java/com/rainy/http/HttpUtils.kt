package com.rainy.http

import androidx.work.WorkManager
import com.rainy.http.request.Request
import com.rainy.http.request.RequestMode
import java.util.UUID


/**
 * @author jhy
 * @description http
 * @date 2021/10/21
 */
object HttpUtils {

    fun get(path: String) = Request(RequestMode.GET, path)

    fun post(path: String) = Request(RequestMode.POST, path)

    fun delete(path: String) = Request(RequestMode.DELETE, path)

    fun put(path: String) = Request(RequestMode.PUT, path)

    fun form(path: String) = Request(RequestMode.FORM, path)

    fun downLoad(url: String) = Request(RequestMode.DOWNLOAD, url)

    fun downLoadCancelAll() {
        WorkManager.getInstance(HttpManager.context).cancelAllWork()
    }

    fun downLoadCancel(tag: String) {
        WorkManager.getInstance(HttpManager.context).cancelAllWorkByTag(tag)
    }

    fun downLoadCancel(id: UUID) {
        WorkManager.getInstance(HttpManager.context).getWorkInfoByIdLiveData(id)
    }
}
