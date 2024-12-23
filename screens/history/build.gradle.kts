plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.orka.history"
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

    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.kotlinx.datetime)

    implementation(project(":lib:ui"))
    implementation(project(":res"))

    implementation(project(":unauthorizer"))
    implementation(project(":http:core"))
    implementation(project(":formatter:core"))

    implementation(project(":models:credentials"))
    implementation(project(":models:products"))
    implementation(project(":models:receive"))
    implementation(project(":models:sale"))

    implementation(project(":datasources:receive:core"))
    implementation(project(":datasources:sale:core"))

    implementation(project(":viewmodels:core"))
    implementation(project(":viewmodels:history"))

    testImplementation(libs.junit)
    
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}