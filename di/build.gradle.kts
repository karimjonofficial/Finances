plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.orka.dependencyinjection"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.squareup.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.room.ktx)
    implementation(project(":lib"))
    implementation(project(":database"))
    implementation(project(":main:data"))
    implementation(project(":main:viewmodels"))
    implementation(project(":features:login:presentation:viewmodels"))
    implementation(project(":features:login:data:base"))
    implementation(project(":features:login:data:network"))
    implementation(project(":features:home:presentation:viewmodels"))
    implementation(project(":features:home:data:base"))
    implementation(project(":features:home:data:network"))
    implementation(project(":features:stock:data:base"))
    implementation(project(":features:stock:data:network"))
    implementation(project(":features:stock:presentation:viewmodels"))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}