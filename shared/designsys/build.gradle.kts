plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
}

android {
    compileSdk = AppConfig.COMPILE_SDK
    defaultConfig {
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Jetpack.COMPOSE
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
    implementation(project(Deps.Module.AOS_UTIL))

    // Kotlin
    implementation(Deps.Kotlin.COROUTINE_CORE)
    implementation(Deps.Kotlin.COROUTINE_ANDROID)

    // Jetpack
    implementation(Deps.Jetpack.NAVIGATION_COMPOSE)
    implementation(Deps.Jetpack.COMPOSE_COMPILER)
    implementation(Deps.Jetpack.COMPOSE_UI)
    debugImplementation(Deps.Jetpack.COMPOSE_UI_TOOLING)
    implementation(Deps.Jetpack.COMPOSE_UI_TOOLING_PREVIEW)
    implementation(Deps.Jetpack.COMPOSE_FOUNDATION)
    implementation(Deps.Jetpack.COMPOSE_MATERIAL)
    implementation(Deps.Jetpack.COMPOSE_ANIMATION)
    debugApi(Deps.Jetpack.CUSTOMVIEW_CORE)
    debugApi(Deps.Jetpack.CUSTOMVIEW_POLLINGCONTAINER)
}