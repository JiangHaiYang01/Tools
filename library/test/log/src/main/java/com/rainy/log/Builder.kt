package com.rainy.log

import android.content.Context
import android.util.Log
import com.rainy.log.adapter.ConsoleAdapter
import com.rainy.log.impl.LoggerAdapter
import com.rainy.log.adapter.DiskAdapter
import com.rainy.log.handler.LoggerHandler
import com.rainy.log.mode.LogConfig
import com.rainy.log.mode.LogLevel
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 构造配置
 *
 * @Author  Allens
 * @Date:   2021/6/24 9:08 下午
 * @Version 1.0
 * @Desc
 */
class Builder(context: Context) {

    private val mInitialize = AtomicBoolean(false)

    companion object {

        private const val TAG = "Logger"

        /**
         * 1M
         */
        private const val ONE_MILLION: Long = (1 * 1024L * 1024L)

        /**
         * 单个文件最小1M
         */
        private const val MIN_SINGLE_FILE_LENGTH: Int = 1

        /**
         * 单个文件大小 10M
         */
        private const val MAX_SINGLE_FILE_LENGTH: Int = 10

        /**
         * 文件最小数量
         */
        private const val MIN_FILE_SIZE = 2

        /**
         * 文件最大数量50
         */
        private const val MAX_FILE_SIZE = 50

    }

    /**
     * 是否打印控制台日志
     */
    private var mConsoleLogOpen = true

    /**
     * 是否保存到文件
     */
    private var mDiskLogOpen = true

    /**
     * 实际保存的log位置
     */
    private var mLogPath = context.getExternalFilesDir(null)?.path + File.separator + "log"

    /**
     * 文件名称前缀
     */
    private var mPreFixName = ""

    /**
     * 单个文件大小
     */
    private var mSingleFileLength: Long = MIN_SINGLE_FILE_LENGTH * ONE_MILLION

    /**
     * 最多保留多少个日志文件
     */
    private var mFileSize = 5

    /**
     * 日志级别
     */
    private var mLogLevel = LogLevel.INFO

    /**
     * 日志适配器
     */
    private val mAdapters = mutableListOf<LoggerAdapter>()

    /**
     * 是否打印控制台日志
     */
    fun setConsoleLogOpen(consoleLogOpen: Boolean) = apply {
        this.mConsoleLogOpen = consoleLogOpen
    }

    /**
     * 是否保存到文件
     */
    fun setDiskLogOPen(isEnable: Boolean) = apply {
        mDiskLogOpen = isEnable
    }

    /**
     * 设置实际保存log位置,此目录存放.log后缀的文件。
     * 建议单独一个目录。不要与cache文件放在同一个目录下，防止文件丢失
     */
    fun setLogPath(path: String) = apply {
        if (path.isNotEmpty()) {
            this.mLogPath = path
        }
    }

    /**
     * 单个文件的大小
     * [length] 单位 M
     */
    fun setSingleFileLength(length: Int) = apply {
        if (length in MIN_SINGLE_FILE_LENGTH..MAX_SINGLE_FILE_LENGTH) {
            this.mSingleFileLength = length * ONE_MILLION
        }
    }

    /**
     * 设置文件最大数量
     */
    fun setMaxFileSize(size: Int) = apply {
        if (size in MIN_FILE_SIZE..MAX_FILE_SIZE) {
            this.mFileSize = size
        }
    }

    /**
     * 设置文件前缀名称
     */
    fun setPeFixName(name: String) = apply {
        if (name.isNotEmpty()) {
            this.mPreFixName = "${name}_"
        }
    }

    /**
     * 设置日志级别
     * @link [LogLevel]
     */
    fun setLogLevel(level: LogLevel) = apply {
        this.mLogLevel = level
    }

    /**
     * 添加日志适配器
     *
     * @param adapter LoggerAdapter
     * @return Builder
     */
    fun addLogAdapter(adapter: LoggerAdapter) = apply {
        mAdapters.add(adapter)
    }

    /**
     * 初始化
     */
    fun init() {
        if (!mInitialize.compareAndSet(false, true)) {
            Log.w(TAG, "Logger is already init")
            return
        }
        Log.i(TAG, "=====================================")
        Log.i(TAG, "level:${mLogLevel}")
        Log.i(TAG, "log path:${mLogPath}")
        Log.i(TAG, "prefix name:${mPreFixName}")
        Log.i(TAG, "console log open:${mConsoleLogOpen}")
        Log.i(TAG, "disk log open:${mDiskLogOpen}")
        Log.i(TAG, "single file size:${mSingleFileLength / ONE_MILLION}M")
        Log.i(TAG, "max file size:${mFileSize}")
        Log.i(TAG, "=====================================")
        LoggerHandler.setConfig(
            LogConfig(
                mLogLevel,
                mFileSize,
                mSingleFileLength,
                mLogPath,
                mPreFixName,
            )
        )
        LoggerHandler.clear()
        if (mConsoleLogOpen) {
            mAdapters.add(ConsoleAdapter())
        }
        if (mDiskLogOpen) {
            mAdapters.add(DiskAdapter())
        }
        mAdapters.forEach {
            LoggerHandler.addAdapter(it)
        }
    }
}