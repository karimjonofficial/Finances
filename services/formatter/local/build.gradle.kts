plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(project(Modules.Services.Formatter.core))
    implementation(project(Modules.Services.Formatter.Datetime.core))
    implementation(project(Modules.Services.Formatter.Currency.core))
}