package com.rainy.log.impl

import com.rainy.log.mode.AppLog


/**
 *
 * @Author  Allens
 * @Date:   2021/6/23 9:45 下午
 * @Version 1.0
 * @Desc
 */
interface LoggerAdapter {

    /**
     * 处理日志信息
     */
    fun print(appLog: AppLog)
}