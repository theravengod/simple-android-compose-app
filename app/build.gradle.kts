val kotlinVersion: String by extra
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("kotlinx-serialization")
}

android {
    compileSdk = 34

    namespace = "kitty.cheshire.playground"

    defaultConfig {
        applicationId = "kitty.cheshire.playground"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
        kotlinOptions {
            jvmTarget = "19"
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeCompilerVersion"] as String
    }
    packagingOptions.resources.excludes.run {
        add("/META-INF/{AL2.0,LGPL2.1}")
        add("/META-INF/atomicfu.kotlin_module")
    }
}

dependencies {
    // Kotlin Core
    implementation("androidx.core:core-ktx:1.12.0")

    // Compose
    val composeBom = platform("androidx.compose:compose-bom:${rootProject.extra["composeBOMVersion"]}")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Material Design 2
    implementation("androidx.compose.material:material")
    // Needed for measurement/layout by Accompanist
    implementation("androidx.compose.ui:ui-util")
    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended")
    // Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    // Integration with activities
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.ui:ui-viewbinding")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose")

    // ViewModel Compose and View (KTX)
    val composeLifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$composeLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$composeLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$composeLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$composeLifecycleVersion")

    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Google
    implementation("com.google.android.material:material:1.11.0")
    // JetBrains
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    // 3rd Party
    // Koin
    val koinVersion = "3.1.2"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    // KTor
    val ktorVersion = "1.5.0"
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Testing stuff
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
