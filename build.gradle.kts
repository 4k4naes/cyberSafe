plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // WYMAGANE OD KOTLIN 2.0
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}


android {
    namespace = "com.example.cybersafeapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.cybersafeapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.activity:activity-compose:1.12.0")
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.ui:ui-tooling:1.6.7")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // WAŻNE – NIE DODAWAJ drugi raz 'androidx.activity'
    // implementation(libs.androidx.activity) ← usunięte
}
