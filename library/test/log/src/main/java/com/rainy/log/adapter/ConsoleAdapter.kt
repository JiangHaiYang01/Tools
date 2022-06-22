package com.rainy.log.adapter

import android.util.Log
import com.rainy.log.mode.LogLevel
import com.rainy.log.impl.LoggerAdapter
import com.rainy.log.mode.AppLog

/**
 * 控制台日志输入
 *
 * @Author  Allens
 * @Date:   2021/6/24 8:35 下午
 * @Version 1.0
 * @Desc
 */
class ConsoleAdapter : LoggerAdapter {

    override fun print(appLog: AppLog) {
        val tag = appLog.tag
        val log = appLog.log
        when (appLog.logLevel) {
            LogLevel.ERROR -> Log.e(tag, log)
            LogLevel.FATAL -> Log.wtf(tag, log)
            LogLevel.INFO -> Log.i(tag, log)
            LogLevel.WARNING -> Log.w(tag, log)
            LogLevel.DEBUG -> Log.d(tag, log)
            LogLevel.VERBOSE -> Log.v(tag, log)
            else -> Log.i(tag, log)
        }
    }
}