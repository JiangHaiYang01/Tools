plugins {
    id("com.android.application")
}
android {
    defaultConfig {
        applicationId = "com.rainy.sample.log"
    }
}
dependencies {
    implementation(project(":log"))
}