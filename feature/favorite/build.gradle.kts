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
    implementation(project(Deps.Module.DOMAIN))
    implementation(project(Deps.Module.KT_UTIL))
    implementation(project(Deps.Module.AOS_UTIL))
    implementation(project(Deps.Module.MVI))
    implementation(project(Deps.Module.DESIGN_SYS))
    implementation(project(Deps.Module.NAVIGATION))
    implementation(project(Deps.Module.THIRD_PARTY))
    implementation(project(Deps.Module.USER_SEARCH))

    // Kotlin
    implementation(Deps.Kotlin.COROUTINE_CORE)
    implementation(Deps.Kotlin.COROUTINE_ANDROID)

    // Jetpack
    implementation(Deps.Jetpack.LIFECYCLE_VIEWMODEL_COMPOSE)
    implementation(Deps.Jetpack.HILT_ANDROID)
    kapt(Deps.Jetpack.HILT_ANDROID_COMPILER)
    implementation(Deps.Jetpack.HILT_NAVIGATION_COMPOSE)
    implementation(Deps.Jetpack.COMPOSE_COMPILER)
    implementation(Deps.Jetpack.COMPOSE_UI)
    debugImplementation(Deps.Jetpack.COMPOSE_UI_TOOLING)
    implementation(Deps.Jetpack.COMPOSE_UI_TOOLING_PREVIEW)
    implementation(Deps.Jetpack.COMPOSE_FOUNDATION)
    implementation(Deps.Jetpack.COMPOSE_MATERIAL)
    implementation(Deps.Jetpack.COMPOSE_ANIMATION)
    debugApi(Deps.Jetpack.CUSTOMVIEW_CORE)
    debugApi(Deps.Jetpack.CUSTOMVIEW_POLLINGCONTAINER)

    // Coil
    implementation(Deps.Coil.CORE)
    implementation(Deps.Coil.GIF)
    implementation(Deps.Coil.SVG)
    implementation(Deps.Coil.VIDEO)
    implementation(Deps.Coil.COMPOSE)
}