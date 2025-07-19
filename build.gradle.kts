// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
    alias(libs.plugins.dagger.hilt)  apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
//    alias(libs.plugins.google.services) apply false
//    alias(libs.plugins.crashlytics) apply false
}