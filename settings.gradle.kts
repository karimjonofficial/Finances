pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Finances"
include(":app")
include(":features:products:presentation:viewmodels")
include(":features:products:data:base")
include(":features:home:data:base")
include(":features:home:presentation:viewmodels")
include(":features:login:data:base")
include(":features:login:presentation:viewmodels")
include(":features:stock:data:base")
include(":features:stock:presentation:viewmodels")
include(":features:home:data:network")
include(":di")
include(":features:login:data:network")
include(":features:stock:data:network")
include(":database")
include(":main:data")
include(":main:viewmodels")
include(":features:home:presentation:composables")
include(":features:login:presentation:composables")
include(":features:stock:presentation:composables")
include(":features:products:presentation:composables")
include(":features:products:data:network")
include(":lib")
include(":lib:test")
include(":lib:ui")
include(":lib:log")
