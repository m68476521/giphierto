
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
}

extensions.configure<com.android.build.api.dsl.LibraryExtension> {
    namespace = "com.m68476521.networking"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(project(":core:models"))

    implementation(libs.kotlinx.serializationJson)
    implementation(libs.ktor.client.core)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.retrofit)

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
