@file:JvmName("Logger")
package com.rainy.log

import android.app.Application
import com.rainy.log.impl.LoggerImpl
import com.rainy.log.handler.LoggerHandler
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * 日志框架
 *
 * @Author  Allens
 * @Date:   2021/6/23 9:23 下午
 * @Version 1.0
 * @Desc
 */
object Logger : LoggerImpl {

    private lateinit var mBuild: Builder

    private val mCreated = AtomicBoolean(false)

    private const val TAG = "Logger"

    /**
     * 创建
     */
    @JvmStatic
    fun create(application: Application): Builder {
        if (!mCreated.get()) {
            mBuild = Builder(application)
            mCreated.getAndSet(true)
        }
        return mBuild
    }

    override fun f(tag: String, format: String, vararg obj: Any?) {
        LoggerHandler.f(tag, format, obj)
    }

    override fun e(tag: String, format: String, vararg obj: Any?) {
        LoggerHandler.e(tag, format, obj)
    }

    override fun i(tag: String, format: String, vararg obj: Any?) {
        LoggerHandler.i(tag, format, obj)
    }

    override fun w(tag: String, format: String, vararg obj: Any?) {
        LoggerHandler.w(tag, format, obj)
    }

    override fun d(tag: String, format: String, vararg obj: Any?) {
        LoggerHandler.d(tag, format, obj)
    }

    override fun v(tag: String, format: String, vararg obj: Any?) {
        LoggerHandler.v(tag, format, obj)
    }
}