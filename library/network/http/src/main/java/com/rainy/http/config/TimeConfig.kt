package com.rainy.http.config

import java.util.concurrent.TimeUnit

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/7/2 21:48
 * @version: 1.0.0
 */
abstract class TimeConfig {
    private companion object {
        const val DEFAULT_TIME = 10L
    }

    var timeUnit: TimeUnit = TimeUnit.SECONDS
    var time: Long = DEFAULT_TIME
}