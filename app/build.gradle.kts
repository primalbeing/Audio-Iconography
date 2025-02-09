plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.audioiconography.app"
    compileSdk = 35 // Keep latest stable compile SDK

    defaultConfig {
        applicationId = "com.audioiconography.app"
        minSdk = 29 // Set to Android 10 (API 29) to avoid legacy issues
        targetSdk = 35 // Targets the latest stable Android version
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Securely load signingConfigs from gradle.properties or environment variables
    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE_FILE") ?: project.findProperty("KEYSTORE_FILE") as String)
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: project.findProperty("KEYSTORE_PASSWORD") as String
            keyAlias = System.getenv("KEY_ALIAS") ?: project.findProperty("KEY_ALIAS") as String
            keyPassword = System.getenv("KEY_PASSWORD") ?: project.findProperty("KEY_PASSWORD") as String
        }
    }

    buildTypes {
        getByName("release") { // Correct way to reference `release`
            signingConfig = signingConfigs.getByName("release") // Proper reference
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Updated for latest compatibility
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17" // Uses Java 17 for better performance
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX Core
    implementation("com.google.android.material:material:1.10.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
