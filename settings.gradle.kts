rootProject.name = "Tools"

// 版本依赖插件
includeBuild("version")

// 测试相关
includeModule("library/test", "log")

includeModule("sample/test", "sample-log")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

/**
 * 引入模块
 * @param path 模块位置
 * @param name 模块名称
 */
fun includeModule(path: String, name: String) {
    include(":$name")
    project(":$name").projectDir = File("$path${File.separator}$name${File.separator}")
}
