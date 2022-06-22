package config

import CommonVersion

/**
 * 主要功能: AndroidX依赖
 * @Description:
 * @author : jhy
 * @date: 2021年08月01日 8:42 下午
 * @version: 1.0.0
 */
object AndroidX {
    private const val coreKtx = "androidx.core:core-ktx:${CommonVersion.AndroidX.coreKTX}"
    private const val appcompat = "androidx.appcompat:appcompat:${CommonVersion.AndroidX.appcompat}"

    val ANDROIDX = arrayListOf<String>().apply {
        add(coreKtx)
        add(appcompat)
    }
}