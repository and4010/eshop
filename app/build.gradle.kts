plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.dagger)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.eshop"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.eshop"
        minSdk = 24
        targetSdk = 35
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
    implementation(libs.androidx.paging.common.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Retrofit
    implementation(libs.retrofit)
// Retrofit with Scalar Converter
    implementation(libs.converter.scalars)


    implementation(libs.coil.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.testing)
    androidTestAnnotationProcessor(libs.hilt.compiler)
    testImplementation(libs.hilt.testing)

    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation (libs.retrofit2.converter.gson)
    implementation(libs.jetbrains.serialization)

    implementation(libs.gson)
}