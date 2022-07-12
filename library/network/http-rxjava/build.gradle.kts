plugins {
    id("com.android.library")
}
dependencies {
    api(project(":http"))
    api("io.reactivex.rxjava3:rxandroid:3.0.0")
    api("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.trello.rxlifecycle4:rxlifecycle-android-lifecycle-kotlin:4.0.2")
}