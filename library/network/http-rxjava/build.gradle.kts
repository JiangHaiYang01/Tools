plugins {
    id("com.android.library")
}
dependencies {
    implementation(project(":http"))
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("o.reactivex.rxjava3:rxjava:3.1.5")
}