package com.rainy.sample_http

import com.rainy.http.HttpPlugins
import com.rainy.http.HttpUtils
import com.rainy.test.ui.SampleAct

class MainActivity : SampleAct() {
    override fun onCreate() {
        addClick("init") {
            HttpPlugins.setBaseUrl("")
                .init()
        }

        addClick("get") {
            HttpUtils.get("")
        }
    }
}