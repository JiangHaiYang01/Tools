package com.rainy.log.mode

/**
 *
 * @Author  Allens
 * @Date:   2021/6/29 7:51 下午
 * @Version 1.0
 * @Desc
 */
data class AppLog(
    val logLevel: LogLevel,
    val tag: String,
    val fileName: String,
    val funcName: String,
    val line: Int,
    val pid: Int,
    val currentThreadId: Long,
    val mainThreadId: Long,
    val log: String
)
