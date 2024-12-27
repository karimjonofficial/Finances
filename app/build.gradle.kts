plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.20"
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "com.orka.finances"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.orka.finances"
        minSdk = 24
        targetSdk = 35
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
        resources.excludes.add("META-INF/*")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.squareup.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)

    implementation(project(Modules.Containers.main))
    implementation(project(Modules.di))
    implementation(project(Modules.res))

    implementation(project(Modules.Lib.Ui.components))

    implementation(project(Modules.unauthorizer))
    implementation(project(Modules.Formatters.core))
    implementation(project(Modules.Fsm.main))

    implementation(project(Modules.Models.credential))

    implementation(project(Modules.ViewModels.core))
    implementation(project(Modules.ViewModels.main))
    implementation(project(Modules.ViewModels.login))
    implementation(project(Modules.ViewModels.home))
    implementation(project(Modules.ViewModels.stock))
    implementation(project(Modules.ViewModels.products))
    implementation(project(Modules.ViewModels.history))
    implementation(project(Modules.ViewModels.basket))

    implementation(project(Modules.Screens.login))
    implementation(project(Modules.Screens.home))
    implementation(project(Modules.Screens.warehouse))
    implementation(project(Modules.Screens.history))
    implementation(project(Modules.Screens.basket))


    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}