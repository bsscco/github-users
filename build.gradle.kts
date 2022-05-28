buildscript {
    repositories {
        google()
    }

    dependencies {
        classpath(Deps.AGP_GRADLE_PLUGIN)
        classpath(Deps.GMS.GOOGLE_SERVICES)
        classpath(Deps.Firebase.CRASHLYTICS_PLUGIN)
        classpath(Deps.Kotlin.GRADLE_PLUGIN)
        classpath(Deps.Jetpack.HILT_GRADLE_PLUGIN)
        classpath(Deps.Jetpack.NAVIGATION_GRADLE_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

plugins {
    id(Plugins.MODULE_DEPENDENCY_GRAPH) version (Versions.MODULE_DEPENDENCY_GRAPH)
}

apply(from = file("gradle/projectDependencyGraph.gradle.kts"))