plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
    id(Plugins.HILT_ANDROID)
}

android {
    compileSdk = AppConfig.COMPILE_SDK
    defaultConfig {
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(Deps.Module.DATA))
    implementation(project(Deps.Module.KT_UTIL))

    // Kotlin
    implementation(Deps.Kotlin.COROUTINE_CORE)
    implementation(Deps.Kotlin.COROUTINE_ANDROID)

    // Jetpack
    api(Deps.Jetpack.ROOM_RUNTIME)
    kapt(Deps.Jetpack.ROOM_COMPILER)
    implementation(Deps.Jetpack.ROOM_KTX)
    implementation(Deps.Jetpack.ROOM_PAGING)
    implementation(Deps.Jetpack.HILT_ANDROID)
    kapt(Deps.Jetpack.HILT_ANDROID_COMPILER)
    implementation(Deps.Jetpack.PAGING_COMMON_KTX)

    // Threeten
    implementation(Deps.Threeten.ABP)
    testImplementation(Deps.Threeten.BP)
}