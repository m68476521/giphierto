// Top-level build file where you can add configuration options common to all sub-projects/modules.

import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
    alias(libs.plugins.dagger.hilt)  apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.detekt)
//    alias(libs.plugins.google.services) apply false
//    alias(libs.plugins.crashlytics) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<DetektExtension> {
        parallel = true
        buildUponDefaultConfig = true
        allRules = false
        autoCorrect = false // Disable detekt auto-correct to let ktlint handle it
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    }
}
