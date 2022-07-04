package com.rainy.http.request

import androidx.annotation.IntDef

/**
 * @author jhy
 * @description 请求方式
 * @date 2021/10/21
 */
@IntDef(
    RequestMode.GET,
    RequestMode.FORM,
    RequestMode.POST,
    RequestMode.PUT,
    RequestMode.DELETE,
    RequestMode.DOWNLOAD,
    RequestMode.UPLOAD,
)
annotation class RequestMode {
    companion object {
        const val GET = 0x01
        const val FORM = 0x02
        const val POST = 0x03
        const val PUT = 0x04
        const val DELETE = 0x05
        const val DOWNLOAD = 0x06
        const val UPLOAD = 0x07
    }
}
