package com.rainy.sample_http

import android.annotation.SuppressLint
import android.util.Log
import com.rainy.http.HttpPlugins
import com.rainy.http.HttpUtils
import com.rainy.http.request.addParam
import com.rainy.http.rxjava.manager.RxJavaFactory
import com.rainy.http.rxjava.request.asResponse
import com.rainy.test.ui.SampleAct
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : SampleAct() {
    companion object {
        private const val TAG = "MainActivity"
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        HttpPlugins
            .setBaseUrl("https://www.wanandroid.com")
            .setFactory(RxJavaFactory())
            .setDebug(true)
            .setLogLevel(HttpLoggingInterceptor.Level.BASIC)
            .setErrorHandler {
                Log.e(TAG, "error:${it.message}")
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
                .addParam("username","rainy")
                .addParam("password","123456")
                .asResponse<String>(this)
                .subscribe { data, error ->
                    Log.i(TAG, "get data:$data")
                    Log.i(TAG, "get error:$error")
                }
        }
    }
}