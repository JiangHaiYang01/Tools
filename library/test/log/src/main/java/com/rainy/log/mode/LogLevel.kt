package com.rainy.log.mode

/**
 * 日志级别
 *
 * @Author Allens
 * @Date: 2021/6/23 9:58 下午
 * @Version 1.0
 * @Desc
 */
enum class LogLevel(@JvmField var level: Int,var info:String) {

    VERBOSE(0,"V"),

    DEBUG(1,"D"),

    INFO(2,"I"),

    WARNING(3,"W"),

    ERROR(4,"E"),

    FATAL(5,"F"),

    NONE(6,"N")
}