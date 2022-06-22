package config

import CommonVersion

/**
 * 主要功能:
 * @Description: 测试依赖库管理
 * @author : jhy
 * @date: 2021年08月01日 1:02 下午
 * @version: 1.0.0
 */
object Testing {

    private const val jUnit = "junit:junit:${CommonVersion.Test.jUnit}"
    private const val androidJunit = "androidx.test.ext:junit:${CommonVersion.Test.androidJunit}"
    private const val espresso = "androidx.test.espresso:espresso-core:${CommonVersion.Test.espresso}"

    val ANDROID_TEST_IMPLEMENTATION = arrayListOf<String>().apply {
        add(androidJunit)
        add(espresso)
    }

    val TEST_IMPLEMENTATION = arrayListOf<String>().apply {
        add(jUnit)
    }
}