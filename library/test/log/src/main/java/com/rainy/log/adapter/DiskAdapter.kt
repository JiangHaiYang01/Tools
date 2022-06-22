package com.rainy.log.adapter

import com.rainy.log.handler.WriterHandler
import com.rainy.log.impl.LoggerAdapter
import com.rainy.log.mode.AppLog

/**
 * 默认输出到文件中
 *
 * @Author  Allens
 * @Date:   2021/6/24 8:49 下午
 * @Version 1.0
 * @Desc
 */
class DiskAdapter : LoggerAdapter {

    private val mWriter = WriterHandler()
    override fun print(appLog: AppLog) {
        mWriter.print(appLog)
    }
}