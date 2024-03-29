plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.samplemovielistcleanarchitecture"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.samplemovielistcleanarchitecture"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = false

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    //core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    //activity
    implementation("androidx.activity:activity-compose:1.8.2")

    //compose
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    //testing and tooling
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.9")

    //DB
    val room_version = "2.6.1"
    implementation("androidx.room:room-common:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //Type converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //AsyncImage
    implementation("io.coil-kt:coil-compose:2.5.0")

    //Di
    val hiltVer = "2.50"
    implementation("com.google.dagger:hilt-android:$hiltVer")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    //annotationProcessor("com.google.dagger:hilt-compiler:$hiltVer")
    kapt("com.google.dagger:hilt-android-compiler:${hiltVer}")

    //androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVer")
    //androidTestAnnotationProcessor("com.google.dagger:hilt-compiler:$hiltVer")

    //testImplementation("com.google.dagger:hilt-android-testing:$hiltVer")
    //testAnnotationProcessor("com.google.dagger:hilt-compiler:$hiltVer")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")


}


