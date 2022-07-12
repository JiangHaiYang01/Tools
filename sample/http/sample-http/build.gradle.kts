plugins {
    id("com.android.application")
}
android {
    defaultConfig {
        applicationId = "com.rainy.sample.http"
    }
}
dependencies {
    api(project(":http-rxjava"))
}