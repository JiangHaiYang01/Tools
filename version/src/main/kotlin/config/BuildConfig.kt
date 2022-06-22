package config

import CommonVersion
/**
 * 编译配置
 */
object BuildConfig {
    const val applicationId = CommonVersion.applicationId
    const val compileSdkVersion = CommonVersion.compileSdkVersion
    const val buildToolsVersion = CommonVersion.buildToolsVersion
    const val minSdkVersion = CommonVersion.minSdkVersion
    const val targetSdkVersion = CommonVersion.targetSdkVersion
    const val versionCode = CommonVersion.versionCode
    const val versionName = CommonVersion.versionName
    const val runner = "androidx.test.runner.AndroidJUnitRunner"
}
