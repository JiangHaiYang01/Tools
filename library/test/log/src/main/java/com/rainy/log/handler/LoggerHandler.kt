package com.rainy.log.handler

import android.os.Looper
import android.os.Process
import android.util.Log
import com.rainy.log.Logger
import com.rainy.log.impl.LoggerImpl
import com.rainy.log.mode.LogLevel
import com.rainy.log.impl.LoggerAdapter
import com.rainy.log.mode.AppLog
import com.rainy.log.mode.LogConfig

/**
 *
 * @Author  Allens
 * @Date:   2021/6/24 9:36 下午
 * @Version 1.0
 * @Desc
 */
object LoggerHandler : LoggerImpl {

    /**
     * 日志的配置
     */
    private lateinit var mConfig: LogConfig

    /**
     * 最小堆栈跟踪索引
     */
    private const val MIN_STACK_OFFSET = 8

    private const val TAG = "LoggerHandler"

    private var mAdapters = mutableSetOf<LoggerAdapter>()

    fun addAdapter(vararg imp: LoggerAdapter) {
        mAdapters.addAll(imp.toList())
    }

    fun getAdapters(): MutableSet<LoggerAdapter> {
        return mAdapters
    }

    fun clear() {
        mAdapters.clear()
    }

    fun setConfig(config: LogConfig) {
        mConfig = config
    }

    fun getConfig(): LogConfig {
        return mConfig
    }

    private fun getStackOffset(trace: Array<StackTraceElement>): Int {
        var i: Int = MIN_STACK_OFFSET
        while (i < trace.size) {
            val element = trace[i]
            val name = element.className
            if (name != LoggerHandler::class.java.name
                && name != LoggerImpl::class.java.name
                && name != Logger::class.java.name
            ) {
                return --i
            }
            i++
        }
        return -1
    }

    private fun print(level: LogLevel, tag: String, format: String, vararg args: Any?) {
        try {
            if (level.level < mConfig.mLogLevel.level) {
                return
            }
            val trace = Thread.currentThread().stackTrace
            val stackIndex: Int = getStackOffset(trace)
            val log = String.format(format, *args)
            mAdapters.forEach {
                it.print(
                    AppLog(
                        if (log.isEmpty()) {
                            LogLevel.DEBUG
                        } else {
                            level
                        },
                        mConfig.preFixName + tag,
                        trace[stackIndex].fileName,
                        trace[stackIndex].methodName,
                        trace[stackIndex].lineNumber,
                        getPid(),
                        getCurrentThreadId(),
                        getMainThreadId(),
                        if (log.isEmpty()) {
                            "this is empty content"
                        } else {
                            log
                        }
                    )
                )
            }
        } catch (t: Throwable) {
            Log.e(TAG, "printer failed ${t.message}")
        }
    }

    override fun f(tag: String, format: String, vararg obj: Any?) {
        print(LogLevel.FATAL, tag, format, obj)
    }

    override fun e(tag: String, format: String, vararg obj: Any?) {
        print(LogLevel.ERROR, tag, format, obj)
    }

    override fun i(tag: String, format: String, vararg obj: Any?) {
        print(LogLevel.INFO, tag, format, obj)
    }

    override fun w(tag: String, format: String, vararg obj: Any?) {
        print(LogLevel.WARNING, tag, format, obj)
    }

    override fun d(tag: String, format: String, vararg obj: Any?) {
        print(LogLevel.DEBUG, tag, format, obj)
    }

    override fun v(tag: String, format: String, vararg obj: Any?) {
        print(LogLevel.VERBOSE, tag, format, obj)
    }

    private fun getCurrentThreadId(): Long {
        return Thread.currentThread().id
    }

    private fun getPid(): Int {
        return Process.myPid()
    }

    private fun getMainThreadId(): Long {
        return Looper.getMainLooper().thread.id
    }
}