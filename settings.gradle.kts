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
include(":database")

include(":containers:main")
include(":containers:singleton")

include(":res")

include(":lib:extensions:string")
include(":lib:extensions:datetime")
include(":lib:http")
include(":lib:log")
include(":lib:tests")
include(":lib:ui:components")
include(":lib:ui:input")
include(":lib:ui:navigation:items")

include(":services:printer:core")
include(":services:http:core")
include(":services:formatter:datetime:core")
include(":services:unauthorizer:core")
include(":services:credential:core")
include(":services:formatter:currency:core")

include(":models:receive")
include(":models:categories")
include(":models:credentials")
include(":models:stock")
include(":models:products")
include(":models:info")
include(":models:basket")
include(":models:sale")

include(":datasources:credential:core")
include(":datasources:categories:core")
include(":datasources:products:core")
include(":datasources:stock:core")
include(":datasources:receive:core")
include(":datasources:credentials:core")
include(":datasources:info:core")
include(":datasources:basket:core")
include(":datasources:sale:core")

include(":datasources:receive:network")
include(":datasources:stock:network")
include(":datasources:categories:network")
include(":datasources:credentials:network")
include(":datasources:products:network")
include(":datasources:basket:local")
include(":datasources:sale:network")

include(":fsm:main")
include(":fsm:core")

include(":viewmodels:home")
include(":viewmodels:login")
include(":viewmodels:main")
include(":viewmodels:core")
include(":viewmodels:history")
include(":viewmodels:basket")
include(":viewmodels:product")

include(":screens:history")
include(":screens:home")
include(":screens:login")
include(":screens:warehouse")
include(":screens:basket")
include(":screens:product")
include(":services:formatter:core")
include(":services:formatter:local")
include(":viewmodels:warehouse")
include(":repositories:basket:core")
