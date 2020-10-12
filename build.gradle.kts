// Top-level build file where you can add configuration options common to all sub-projects/modules.
import extentions.applyDefault


buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("${BuildPluginsClasspath.GRADLE_FOR_ANDROID}")
        classpath("${BuildPluginsClasspath.GRADLE_FOR_KOTLIN}")
        classpath("${BuildPluginsClasspath.SAFEARGS}")
    }
}

allprojects {
    repositories.applyDefault()
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
