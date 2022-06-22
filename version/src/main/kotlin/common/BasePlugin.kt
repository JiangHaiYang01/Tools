package common

import androidTestImplementation
import com.android.build.gradle.*
import config.AndroidX
import config.BuildConfig
import config.Google
import config.Testing
import implementation
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.getByType
import testImplementation

/**
 * 主要功能:
 * @Description: 插件管理
 * @author : jhy
 * @date: 2021年08月01日 1:02 下午
 * @version: 1.0.0
 */
abstract class BasePlugin : Plugin<Project> {
    private var actionApp: (Project.() -> Unit)? = null

    private var actionLibrary: (Project.() -> Unit)? = null

    private var actionCommon: (Project.() -> Unit)? = null

    override fun apply(project: Project) {
        onCreate(project)
        project.plugins.config(project)
    }

    abstract fun onCreate(project: Project)

    private fun PluginContainer.config(project: Project) {
        whenPluginAdded {
            when (this) {
                // com.android.application
                is AppPlugin -> {
                    applyApp(project)
                }
                // com.android.library
                is LibraryPlugin -> {
                    applyLibrary(project)
                }
            }
        }
    }

    private fun applyLibrary(project: Project) {
        applyCommon(project)
        // 公共 android 配置项
        project.extensions.getByType<LibraryExtension>().applyLibraryCommons()
        actionLibrary?.invoke(project)
    }

    private fun applyApp(project: Project) {
        applyCommon(project)
        // 公共 android 配置项
        project.extensions.getByType<AppExtension>().applyAppCommons()
        actionApp?.invoke(project)
    }

    private fun applyCommon(project: Project) {
        // 公共插件
        project.applyCommonPlugin()
        // 公共依赖
        project.applyCommonDependencies()
        actionCommon?.invoke(project)
    }

    /**
     * 公共依赖
     */
    private fun Project.applyCommonDependencies() {
        dependencies.apply {
            add("api", fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
            applyTestDependencies()
            applyCommonDependencies()
        }
    }

    /**
     * test 依赖配置
     */
    private fun DependencyHandler.applyTestDependencies() {
        testImplementation(Testing.TEST_IMPLEMENTATION)
        androidTestImplementation(Testing.ANDROID_TEST_IMPLEMENTATION)
    }

    /**
     * 公共都需要的依赖库
     */
    private fun DependencyHandler.applyCommonDependencies() {
        implementation(AndroidX.ANDROIDX)
        add("implementation", Google.material)
    }

    /**
     * 公共kotlin 插件
     */
    private fun Project.applyCommonPlugin() {
        plugins.apply("kotlin-android")
        plugins.apply("kotlin-kapt")
        plugins.apply("org.jetbrains.kotlin.android")
    }

    /**
     * app Module 配置项
     */
    private fun AppExtension.applyAppCommons() {
        defaultConfig { applicationId = BuildConfig.applicationId }
        applyBuildConfig()
    }

    /**
     * library Module 配置项
     */
    private fun LibraryExtension.applyLibraryCommons() {
        applyBuildConfig()
    }

    /**
     * defaultConfig
     */
    private fun BaseExtension.applyBuildConfig() {
        compileSdkVersion(BuildConfig.compileSdkVersion)
        buildToolsVersion(BuildConfig.buildToolsVersion)

        defaultConfig {
            minSdk = BuildConfig.minSdkVersion
            targetSdk = BuildConfig.targetSdkVersion
            versionCode = BuildConfig.versionCode
            versionName = BuildConfig.versionName

            testInstrumentationRunner = BuildConfig.runner
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    fun addAppPlugin(action: Project.() -> Unit) {
        actionApp = action
    }

    fun addLibraryPlugin(action: Project.() -> Unit) {
        actionApp = action
    }

    fun addCommonPlugin(action: Project.() -> Unit) {
        actionCommon = action
    }
}
