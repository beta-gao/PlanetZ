plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2"
}

android {
    namespace = "com.example.planetz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.planetz"
        minSdk = 24
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-firestore:25.1.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.recyclerview)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.recyclerview.v110)
    implementation (libs.ssp.android)
    implementation (libs.sdp.android)
    implementation("androidx.work:work-runtime:2.8.1")

}