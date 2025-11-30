plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.googleServices)
}


android {
    namespace = "com.example.chakrivison"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.chakrivison"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended:1.7.5")
    implementation("androidx.navigation:navigation-compose:2.8.4")
    implementation("io.coil-kt:coil-compose:2.6.0")

    // --------------------------
    // ✅ Firebase (Correct)
    // --------------------------
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")

    // --------------------------
    // ✅ Google Sign-In (Correct)
    // --------------------------
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // ❌ REMOVED BAD DEPENDENCIES
    // implementation(libs.androidx.credentials)
    // implementation(libs.androidx.credentials.play.services.auth)
    // implementation(libs.googleid)
    // implementation(libs.firebase.auth)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    debugImplementation("org.mockito:mockito-core:5.12.0")
}
