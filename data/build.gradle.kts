import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}


val credentialsPropFile = rootProject.file("credentials.properties")
val credentials = Properties().apply {
    load(credentialsPropFile.inputStream())
}

val weatherApiKey = credentials["WEATHER_API_KEY"] as String

android {
    namespace = "com.xheghun.climatetracker.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "EXCHANGE_API_KEY", weatherApiKey)
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

    buildFeatures {
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))

    //retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.squareup.gson)

    //datastore
    implementation(libs.androidx.datastore)

    //koin
    implementation(libs.koin.core)
    //implementation(libs.koin.android)

    //test
    testImplementation(libs.mock)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}