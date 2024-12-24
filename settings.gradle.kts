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
include(":lib")
include(":lib:tests")
include(":lib:ui")
include(":lib:log")
include(":di")
include(":app")
include(":res")
include(":datasources:receive:core")
include(":models:receive")
include(":datasources:categories:core")
include(":datasources:products:core")
include(":datasources:stock:core")
include(":models:categories")
include(":models:credentials")
include(":models:stock")
include(":models:products")
include(":models:info")
include(":datasources:credentials:core")
include(":datasources:info:core")
include(":viewmodels:home")
include(":viewmodels:login")
include(":viewmodels:stock")
include(":viewmodels:products")
include(":screens:home")
include(":screens:login")
include(":screens:products")
include(":screens:stock")
include(":viewmodels:main")
include(":http:core")
include(":database")
include(":datasources:receive:network")
include(":datasources:stock:network")
include(":datasources:categories:network")
include(":datasources:credentials:network")
include(":datasources:products:network")
include(":viewmodels:core")
include(":unauthorizer")
include(":screens:warehouse")
include(":screens:history")
include(":viewmodels:history")
include(":datasources:basket:core")
include(":models:basket")
include(":viewmodels:basket")
include(":datasources:basket:local")
include(":screens:basket")
include(":datasources:sale:core")
include(":models:sale")
include(":formatter:core")
include(":formatter:uz")
include(":datasources:sale:network")
include(":lib:extensions:string")
