package com.rainy.sample_log

import com.rainy.log.Logger
import com.rainy.test.ui.SampleAct


class KotlinMainActivity : SampleAct() {
    companion object{
        const val TAG  ="KotlinMainActivity"
    }

    override fun onCreate() {
        addClick("初始化") { init() }
        addClick("写100个日志") { write100() }
        addClick("写1个日志") { write1() }
        addClick("写循环") { writeWhile() }
    }

    private fun writeWhile() {
        for (i in 1..1000) {
            for (index in 'a'..'z') {
                Logger.i(TAG, "$index")
            }
            for (index in 1..10) {
                Logger.i(TAG, "$index")
            }
            for (index in 'A'..'Z') {
                Logger.i(TAG, "$index")
            }
        }
    }

    private fun write1() {
        Logger.i(TAG, "Hello World")
    }

    private fun write100() {
        for (index in 1..100) {
            Logger.i(TAG, "这是一个测试的日志 index:$index")
        }
    }

    private fun init() {
        Logger.create(application)
            // 是否打印控制台日志
            // .setConsoleLogOpen(true)
            // 设置日志级别
            // .setLogLevel(LogLevel.DEBUG)
            // 设置文件前缀名称
            // .setPeFixName("Logger")
            // 设置文件最大数量
            // .setMaxFileSize(2)
            // 单个文件的大小
            // .setSingleFileLength(2)
            // 添加自定义的适配器
            // .addLogAdapter()
            // 设置实际保存log位置
            // .setLogPath()
            .init()
    }
}