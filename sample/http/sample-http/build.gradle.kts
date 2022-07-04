plugins {
    id("com.android.application")
}
android {
    defaultConfig {
        applicationId = "com.rainy.sample.http"
    }
}
dependencies {
    implementation(project(":http"))
    implementation(project(":http-rxjava"))
}