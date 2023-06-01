plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
}

android {
    namespace = "com.giussepr.pokeapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.giussepr.pokeapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx)
    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Compose
    implementation(libs.bundles.compose)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)
    // Lottie
    implementation(libs.lottie.compose)
    // Logging interceptor
    implementation(libs.logging.interceptor)
    // Apollo GraphQL
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.mockserver)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
    // Hilt and instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    // Hilt and Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)

    // Coil
    implementation(libs.coil)

    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

apollo {
    service("service") {
        packageName.set("com.giussepr.pokeserver")
    }
}
