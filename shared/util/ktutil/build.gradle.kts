plugins {
    id(Plugins.KOTLIN)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    // Kotlin
    implementation(Deps.Kotlin.COROUTINE_CORE)

    // Java
    implementation(Deps.JavaX.INJECT)
}