plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.reansen.simple_pos_application"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.reansen.simple_pos_application"
        minSdk = 28
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    //Room
    implementation ("androidx.room:room-ktx:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    //Zxing barcode scanner
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}