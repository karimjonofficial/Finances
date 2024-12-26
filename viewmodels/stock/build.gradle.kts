plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.orka.stock"
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
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.squareup.retrofit)

    implementation(project(Modules.Lib.log))

    implementation(project(Modules.Http.Service.core))
    implementation(project(Modules.ViewModels.core))

    implementation(project(Modules.Models.receive))
    implementation(project(Modules.Models.stock))
    implementation(project(Modules.Models.product))
    implementation(project(Modules.Models.basket))

    implementation(project(Modules.DataSources.Receive.core))
    implementation(project(Modules.DataSources.Product.core))
    implementation(project(Modules.DataSources.Basket.core))
    implementation(project(Modules.DataSources.Stock.core))

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(project(Modules.Lib.tests))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}