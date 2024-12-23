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
    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(project(":unauthorizer"))
    implementation(project(":database"))

    implementation(project(":http:core"))

    implementation(project(":formatter:core"))
    implementation(project(":formatter:uz"))

    implementation(project(":models:info"))
    implementation(project(":models:credentials"))
    implementation(project(":models:receive"))
    implementation(project(":models:categories"))
    implementation(project(":models:products"))
    implementation(project(":models:stock"))
    implementation(project(":models:basket"))
    implementation(project(":models:sale"))

    implementation(project(":datasources:info:core"))
    implementation(project(":datasources:credentials:core"))
    implementation(project(":datasources:receive:core"))
    implementation(project(":datasources:categories:core"))
    implementation(project(":datasources:products:core"))
    implementation(project(":datasources:stock:core"))
    implementation(project(":datasources:basket:core"))
    implementation(project(":datasources:sale:core"))

    implementation(project(":datasources:credentials:network"))
    implementation(project(":datasources:receive:network"))
    implementation(project(":datasources:categories:network"))
    implementation(project(":datasources:products:network"))
    implementation(project(":datasources:stock:network"))
    implementation(project(":datasources:basket:local"))
    implementation(project(":datasources:sale:network"))

    implementation(project(":viewmodels:core"))
    implementation(project(":viewmodels:main"))
    implementation(project(":viewmodels:login"))
    implementation(project(":viewmodels:home"))
    implementation(project(":viewmodels:products"))
    implementation(project(":viewmodels:stock"))
    implementation(project(":viewmodels:history"))
    implementation(project(":viewmodels:basket"))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}