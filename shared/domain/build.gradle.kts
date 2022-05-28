plugins {
    id(Plugins.KOTLIN)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(Deps.Module.KT_UTIL))

    // Kotlin
    implementation(Deps.Kotlin.COROUTINE_CORE)
    implementation(Deps.Kotlin.COROUTINE_ANDROID)
    testImplementation(Deps.Kotlin.COROUTINE_TEST)

    // Jetpack
    implementation(Deps.Jetpack.PAGING_COMMON_KTX)

    // Java
    implementation(Deps.JavaX.INJECT)

    // Threeten
    implementation(Deps.Threeten.ABP)
    testImplementation(Deps.Threeten.BP)

    // JUnit
    testImplementation(Deps.JUnit.CORE)
}