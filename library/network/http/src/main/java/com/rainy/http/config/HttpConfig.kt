package com.rainy.http.config

/**
 * 主要功能: 网络配置Config
 * @Description:
 * @author : jhy
 * @date: 2021年08月07日 1:23 下午
 * @version: 1.0.0
 */
data class HttpConfig(

    /** baseUrl */
    var baseUrl: String = "",

    /** 连接超时 */
    var connectTime: TimeConfig = TimeConfig(),

    /** 写入超时 */
    var writeTime: TimeConfig = TimeConfig(),

    /** 读取超时 */
    var readTime: TimeConfig = TimeConfig(),

    /** 配置通用的请求头 */
    var heard: HashMap<String, String> = HashMap(),

    /** 日志 */
//    var log: LogConfig = LogConfig(),

    /** 是否是DEBUG */
    var debug :Boolean =  true
)





