import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ktlint)
//    alias(libs.plugins.google.services)
//    alias(libs.plugins.crashlytics)

    kotlin("kapt")
}

fun getLocalProperties(): Properties =
    Properties().apply {
        val file = rootProject.file("gradle.properties")
        if (file.exists()) {
            file.inputStream().use { load(it) }
        }
    }

android {
    namespace = "com.m68476521.giphiertwo"
    compileSdk = 35
    signingConfigs {
//        release {
//            keyAlias project.property("keyAlias")
//            keyPassword project.property("keyPassword")
//            storeFile file(project.property("storeFile"))
//            storePassword project.property("storePassword")
//        }
        create("release") {
            // You can name your configuration as needed
            val properties = getLocalProperties()
            storeFile = file("../giphierto_key_store.jks") // Replace with your keystore file path
            storePassword = properties.getProperty("storePassword") // Replace with your keystore password
            keyAlias = properties.getProperty("keyAlias") // Replace with your key alias
            keyPassword = properties.getProperty("keyPassword") // Replace with your key password
        }
    }

    defaultConfig {
        applicationId = "com.m68476521.giphiertwo"
        minSdk = 24
        targetSdk = 35
        versionCode = 10
        versionName = "2.0.2"
    }

    buildTypes {
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type.
            isMinifyEnabled = true
//            signingConfig = signingConfigs.release
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "BASE_URL", "\"https://api.giphy.com\"")

            val properties = getLocalProperties()
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("apiKey")}\"")

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
//            shrinkResources = true
            isShrinkResources = true

            // Other parameters
//            debuggable = false
//            jniDebuggable = false
//            renderscriptDebuggable = false
//            pseudoLocalesEnabled = false
//            zipAlignEnabled = true

//            manifestPlaceholders = [crashlyticsCollectionEnabled:"true"]
        }
        getByName("debug") {
//            debuggable = true
//            versionNameSuffix = '-DEBUG'
            buildConfigField("String", "BASE_URL", "\"https://api.giphy.com\"")

            val properties = getLocalProperties()
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("apiKey")}\"")

//            manifestPlaceholders = [crashlyticsCollectionEnabled:"false"]
//            ext.enableCrashlytics=false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    buildFeatures {
        buildConfig = true

        dataBinding = true
        viewBinding = true

        // Enables Jetpack Compose for this module
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    implementation(libs.kotlin.stdlib)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Networking
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.adapter.rxjava2)
    implementation(libs.converter.gson)
    implementation(libs.gson)

    implementation(libs.glide)
    ksp(libs.glide.compiler)

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    androidTestImplementation(libs.androidx.rules)

    implementation(libs.androidx.room.runtime)

    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    implementation(libs.kotlinx.coroutines.core)
//    implementation platform("com.google.firebase:firebase-bom:33.16.0")
    implementation(libs.firebase.bom)

//    implementation(libs.firebase.analytics)
    implementation(libs.androidx.lifecycle.livedata.ktx)

//    implementation(libs.lifecycle.extensions)
//    kapt "android.arch.lifecycle:compiler:$archLifecycleVersion"

    implementation(libs.androidx.lifecycle.common.java8)

    // Paging 3
    implementation(libs.androidx.paging.runtime)

    // Jetpack Compose Integration
    implementation(libs.androidx.paging.compose)

    // ViewPager2
    implementation(libs.androidx.viewpager2)

    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation(libs.leakcanary.android)

    // Hilt for dependency injection
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.hilt.lifecycle.viewmodel)

    // Dexter permission lib
    implementation(libs.dexter)

    // Material Design
    implementation(libs.material)

    // Integration with activities
    implementation(libs.androidx.activity.compose)
    // Compose Material Design
    implementation(libs.androidx.material)
    // Animations
    implementation(libs.androidx.animation)
    // Tooling support (Previews, etc.)
    implementation(libs.androidx.ui.tooling)
    // Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // UI Tests
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(libs.androidx.ui)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.androidx.foundation)
    // Material design icons
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    // Integration with observables
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.runtime.rxjava2)

    // When using a MDC theme
    implementation(libs.compose.theme.adapter)

    // When using a AppCompat theme
    implementation(libs.accompanist.appcompat.theme)
}
