package com.rainy.sample_http

import android.annotation.SuppressLint
import android.util.Log
import com.rainy.http.HttpConfig
import com.rainy.http.HttpUtils
import com.rainy.http.request.addHeard
import com.rainy.http.request.addParam
import com.rainy.http.request.setUrl
import com.rainy.http.rxjava.manager.RxJavaFactory
import com.rainy.http.rxjava.request.asDownLoad
import com.rainy.http.rxjava.request.asResponse
import com.rainy.test.ui.SampleAct
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

class MainActivity : SampleAct() {
    companion object {
        private const val TAG = "MainActivity"
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        HttpConfig
            .setBaseUrl("https://www.wanandroid.com")
            .setFactory(RxJavaFactory())
            .setDebug(true)
            .setLogLevel(HttpLoggingInterceptor.Level.NONE)
            .setErrorHandler {
                Log.e(TAG, "error:${it.message}")
            }
            .addHeard("x-common", "uuid")
            .addLogInterceptor {
                // Log.i(TAG, "--->$it")
            }
            .init(application)

        addClick("get") {
            HttpUtils.get("friend/json")
                .asResponse<String>()
                .subscribe { data, error ->
                    Log.i(TAG, "get data:$data")
                    Log.i(TAG, "get error:$error")
                }
        }

        addClick("post") {
            HttpUtils.post("user/login")
                .addParam("username", "rainy")
                .addParam("password", "123456")
                .setUrl("http://xxxxx")
                .addHeard("x-type", "post")
                .asResponse<String>(this)
                .subscribe { data, error ->
                    Log.i(TAG, "get data:$data")
                    Log.i(TAG, "get error:$error")
                }
        }

        addClick("download") {
            val destPath = getExternalFilesDir(null)?.path + File.separator + "download"
            val request = HttpUtils.downLoad("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
            request.asDownLoad(this, destPath, "meinv.mp4",tag = "tag")
                .onSuccess {
                    Log.i(TAG, "success:${it.path}")
                }
                .onFailure {
                    Log.i(TAG, "failed:${it.throwable}")
                }
                .onCancel {
                    Log.i(TAG, "cancel:${it}")
                }
                .onProgress {
                    Log.i(TAG, "progress:${it.progress}")
                }
                .onPause {
                    Log.i(TAG, "pause:${it}")
                }
        }

        addClick("cancel with tag") {
            HttpUtils.donwLoadCancel("tag")
        }
    }
}