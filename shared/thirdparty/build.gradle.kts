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
    buildTypes {
        debug {
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
        }
        release {
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
        }
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
    implementation(project(Deps.Module.KT_UTIL))

    // Kotlin
    implementation(Deps.Kotlin.COROUTINE_CORE)
    implementation(Deps.Kotlin.COROUTINE_ANDROID)

    // Jetpack
    implementation(Deps.Jetpack.LIFECYCLE_PROCESS)
    implementation(Deps.Jetpack.CORE_KTX)
    implementation(Deps.Jetpack.HILT_ANDROID)
    kapt(Deps.Jetpack.HILT_ANDROID_COMPILER)

    // Firebase
    implementation(platform(Deps.Firebase.BOM))
    implementation(Deps.Firebase.CRASHLYTICS)
    implementation(Deps.Firebase.CRASHLYTICS_NDK)
    implementation(Deps.Firebase.ANALYTICS)
}