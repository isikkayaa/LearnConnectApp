plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

kapt {
    correctErrorTypes = true
    arguments {
        arg("room.schemaLocation", "$rootDir/schemas")
    }
}



android {
    namespace = "com.example.learnconnectapp"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }




    defaultConfig {
        applicationId = "com.example.learnconnectapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign("room.schemaLocation" to "$projectDir/schemas".toString())
            }
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
}

kapt {
    correctErrorTypes = true

}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.preference:preference:1.2.1")
    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
    implementation("androidx.test.ext:junit-ktx:1.2.1")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    testImplementation ("androidx.test:core:1.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    testImplementation("org.mockito:mockito-core:4.8.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:4.8.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")


    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    testImplementation("com.google.dagger:hilt-android-testing:2.48.1")
    kaptTest("com.google.dagger:hilt-android-compiler:2.48.1")

    implementation ("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation ("com.google.firebase:firebase-auth:23.0.0")
    implementation ("com.google.android.gms:play-services-auth:21.2.0")
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")

    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.github.bumptech.glide:glide:4.13.2")
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")


    implementation ("com.google.android.exoplayer:exoplayer:2.19.0")
    implementation("com.airbnb.android:lottie:3.4.0")


    implementation ("at.favre.lib:bcrypt:0.9.0")


}