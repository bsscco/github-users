plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.GOOGLE_PLAY_SERVICES)
    id(Plugins.FIREBASE_CRASHLYTICS)
    kotlin(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
    id(Plugins.HILT_ANDROID)
}

android {
    compileSdk = AppConfig.COMPILE_SDK
    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    buildTypes {
        debug {
            isMinifyEnabled = BuildFlags.Debug.MINIFY_ENABLED
            isShrinkResources = BuildFlags.Debug.SHRINK_RESOURCES
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Deps.Module.DOMAIN))
    implementation(project(Deps.Module.DATA))
    implementation(project(Deps.Module.REMOTE))
    implementation(project(Deps.Module.LOCAL))
    implementation(project(Deps.Module.KT_UTIL))
    implementation(project(Deps.Module.AOS_UTIL))
    implementation(project(Deps.Module.THIRD_PARTY))
    implementation(project(Deps.Module.DESIGN_SYS))
    implementation(project(Deps.Module.NAVIGATION))
    implementation(project(Deps.Module.HOME))
    implementation(project(Deps.Module.USER_SEARCH))
    implementation(project(Deps.Module.USER_DETAIL))
    implementation(project(Deps.Module.FAVORITE))

    // Kotlin
    implementation(Deps.Kotlin.COROUTINE_CORE)
    implementation(Deps.Kotlin.COROUTINE_ANDROID)

    // Jetpack
    implementation(Deps.Jetpack.APP_COMPAT)
    implementation(Deps.Jetpack.NAVIGATION_COMPOSE)
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

    // Accompanist
    implementation(Deps.Accompanist.NAVIGATION_ANIMATION)
}