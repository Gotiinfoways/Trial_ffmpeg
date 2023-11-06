plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.trial"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.trial"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    //view Binding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    //sdp
    implementation ("com.intuit.sdp:sdp-android:1.1.0")

    //merge FFmpeg
    implementation ("com.arthenica:mobile-ffmpeg-full:4.4")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

//    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.22")


    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.20")

    //seekbar
//    implementation ("com.github.guilhe:seekbar-ranged-view:1.0.3")
//    implementation ("com.github.Mohammed-Alaa:RangeSeekBar:1.0.1")
//    implementation ("com.github.MohammedAlaaMorsi:RangeSeekBar:1.0.6")

//    implementation ("com.yahoo.mobile.client.android.util.rangeseekbar:rangeseekbar-library:0.1.0")
//    implementation ("com.yahoo.mobile.client.android.util.rangeseekbar:rangeseekbar-library:0.2.0-SNAPSHOT")
//    implementation ("com.github.Jay-Goo:RangeSeekBar:3.0.0")

//    implementation ("com.chemicalbird.android:range-seekview:0.0.4")

//    implementation ("com.github.guilhe:seekbar-ranged-view:3.0.0")
    implementation ("com.github.guilhe:seekbar-ranged-view:2.0.5")
}