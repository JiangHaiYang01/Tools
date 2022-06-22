package com.rainy.log.impl


/**
 *
 * @Author  Allens
 * @Date:   2021/6/24 8:20 下午
 * @Version 1.0
 * @Desc
 */
interface LoggerImpl {

    fun f(tag: String, format: String, vararg obj: Any?)

    fun f(tag: String, msg: String) {
        f(tag, msg, null)
    }

    fun e(tag: String, format: String, vararg obj: Any?)

    fun e(tag: String, msg: String) {
        e(tag, msg, null)
    }

    fun i(tag: String, format: String, vararg obj: Any?)

    fun i(tag: String, msg: String) {
        i(tag, msg, null)
    }

    fun w(tag: String, format: String, vararg obj: Any?)

    fun w(tag: String, msg: String) {
        w(tag, msg, null)
    }

    fun d(tag: String, format: String, vararg obj: Any?)

    fun d(tag: String, msg: String) {
        d(tag, msg, null)
    }

    fun v(tag: String, format: String, vararg obj: Any?)

    fun v(tag: String, msg: String) {
        v(tag, msg, null)
    }
}