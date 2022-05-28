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
    testImplementation(Deps.Kotlin.COROUTINE_TEST)

    // Jetpack
    implementation(Deps.Jetpack.HILT_ANDROID)
    kapt(Deps.Jetpack.HILT_ANDROID_COMPILER)

    // Retrofit
    implementation(Deps.Retrofit.CORE)
    implementation(Deps.Retrofit.CONVERTER_GSON)

    // Okhttp
    implementation(Deps.Okhttp.CORE)
    implementation(Deps.Okhttp.LOGGING_INTERCEPTOR)

    // Threeten
    implementation(Deps.Threeten.ABP)
    testImplementation(Deps.Threeten.BP)

    // JUnit
    testImplementation(Deps.JUnit.CORE)

    // Mockk
    testImplementation(Deps.Mockk.CORE)
}