plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.orka.singleton"
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

    implementation(libs.squareup.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.coil.network.okhttp)

    implementation(project(Modules.Lib.http))

    implementation(project(Modules.Services.Credential.core))
    implementation(project(Modules.Services.Http.core))
    implementation(project(Modules.Services.Printer.core))
    implementation(project(Modules.Services.Formatter.Currency.core))
    implementation(project(Modules.Services.Formatter.Datetime.core))
    implementation(project(Modules.Services.Formatter.core))
    implementation(project(Modules.Services.Formatter.local))
    implementation(project(Modules.database))

    implementation(project(Modules.Fsm.core))

    implementation(project(Modules.Models.info))
    implementation(project(Modules.Models.credential))

    implementation(project(Modules.DataSources.Info.core))
    implementation(project(Modules.DataSources.Credential.core))
    implementation(project(Modules.DataSources.Receive.core))
    implementation(project(Modules.DataSources.Category.core))
    implementation(project(Modules.DataSources.Product.core))
    implementation(project(Modules.DataSources.Stock.core))
    implementation(project(Modules.DataSources.Basket.core))
    implementation(project(Modules.DataSources.Sale.core))

    implementation(project(Modules.DataSources.Credential.network))
    implementation(project(Modules.DataSources.Receive.network))
    implementation(project(Modules.DataSources.Category.network))
    implementation(project(Modules.DataSources.Product.network))
    implementation(project(Modules.DataSources.Stock.network))
    implementation(project(Modules.DataSources.Basket.local))
    implementation(project(Modules.DataSources.Sale.network))

    implementation(project(Modules.ViewModels.core))
    implementation(project(Modules.ViewModels.login))
    implementation(project(Modules.ViewModels.home))
    implementation(project(Modules.ViewModels.product))
    implementation(project(Modules.ViewModels.history))
    implementation(project(Modules.ViewModels.basket))
    implementation(project(Modules.ViewModels.warehouse))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}