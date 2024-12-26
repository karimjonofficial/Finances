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


    implementation(project(Modules.res))
    implementation(project(Modules.Lib.Ui.components))
    implementation(project(Modules.Lib.Ui.Navigation.items))
    implementation(project(Modules.Lib.log))
    implementation(project(Modules.Lib.Extensions.string))

    implementation(project(Modules.unauthorizer))
    implementation(project(Modules.Formatters.core))

    implementation(project(Modules.Http.Service.core))
    implementation(project(Modules.ViewModels.core))

    implementation(project(Modules.Models.receive))
    implementation(project(Modules.Models.product))
    implementation(project(Modules.Models.sale))

    implementation(project(Modules.ViewModels.history))

    testImplementation(libs.junit)
    
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}