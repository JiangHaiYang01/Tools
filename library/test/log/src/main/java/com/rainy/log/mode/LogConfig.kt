package com.rainy.log.mode


/**
 *
 * @Author  Allens
 * @Date:   2021/6/27 11:48 下午
 * @Version 1.0
 * @Desc
 */
data class LogConfig(
    var mLogLevel: LogLevel,
    var maxFileSize: Int,
    var maxFileLength: Long,
    var logPath: String,
    var preFixName: String
)
