import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.androidTestImplementation
import org.gradle.kotlin.dsl.kaptAndroidTest
import org.gradle.kotlin.dsl.libs
import org.gradle.kotlin.dsl.testImplementation
import org.jetbrains.kotlin.gradle.utils.addExtendsFromRelation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.example.notesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.notesapp"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //retrofit and gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // For instrumentation tests
    androidTestImplementation(libs.google.hilt.android.testing)
    kaptAndroidTest(libs.dagger.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //ROOM DATABASE
    val room_version = "2.6.1"
    implementation(libs.androidx.room.runtime)

    // For local unit tests
    testImplementation(libs.google.hilt.android.testing)
    kaptTest(libs.dagger.hilt.compiler)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)
     implementation(libs.kotlinx.serialization.json.jvm)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}
kapt {
correctErrorTypes =true
}