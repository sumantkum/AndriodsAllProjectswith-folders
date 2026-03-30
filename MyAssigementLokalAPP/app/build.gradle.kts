plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // ✅ Compose compiler plugin (WITH VERSION)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"

    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.myassigementlokalapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myassigementlokalapp"
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

    kotlinOptions {
        jvmTarget = "11"
    }

    // ✅ REQUIRED FOR COMPOSE
    buildFeatures {
        compose = true
    }

    // ✅ VERY IMPORTANT (fix remember() error)
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {

    //Jetpack Compose (BOM)
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))

    implementation("androidx.activity:activity-compose:1.8.2")

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // 🔹 ViewModel for Compose (REQUIRED)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    debugImplementation("androidx.compose.ui:ui-tooling")

    // 🔹 Core Android
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    // 🔹 Timber (optional but fine)
    implementation("com.jakewharton.timber:timber:5.0.1")

    // 🔹 Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // 🔹 Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
