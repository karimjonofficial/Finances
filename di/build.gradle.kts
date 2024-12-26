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

    implementation(project(Modules.unauthorizer))
    implementation(project(Modules.database))

    implementation(project(Modules.Http.Service.core))
    implementation(project(Modules.Http.status))

    implementation(project(Modules.Formatters.core))
    implementation(project(Modules.Formatters.uz))

    implementation(project(Modules.Models.info))
    implementation(project(Modules.Models.credential))
    /**implementation(project(Modules.Models.receive))
    implementation(project(Modules.Models.category))
    implementation(project(Modules.Models.product))
    implementation(project(Modules.Models.stock))
    implementation(project(Modules.Models.basket))
    implementation(project(Modules.Models.sale))**/

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
    implementation(project(Modules.ViewModels.main))
    implementation(project(Modules.ViewModels.login))
    implementation(project(Modules.ViewModels.home))
    implementation(project(Modules.ViewModels.products))
    implementation(project(Modules.ViewModels.stock))
    implementation(project(Modules.ViewModels.history))
    implementation(project(Modules.ViewModels.basket))


    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}