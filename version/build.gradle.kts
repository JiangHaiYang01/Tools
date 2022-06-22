plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly("com.android.tools.build:gradle:7.1.3")
}

gradlePlugin {
    plugins {
        create("version") {
            // 在 app 模块需要通过 id 引用这个插件
            id = "com.rainy.version"
            implementationClass = "VersionPlugin"
        }
    }
}