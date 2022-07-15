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
            .setLogLevel(HttpLoggingInterceptor.Level.BODY)
            .setErrorHandler {
                Log.e(TAG, "error:${it.message}")
            }
            .addHeard("x-common", "uuid")
            .addLogInterceptor {
                // Log.i(TAG, "--->$it")
            }
            .init(applicationContext)

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
            HttpUtils.downLoad("user/login")
                .asDownLoad()
        }
    }
}