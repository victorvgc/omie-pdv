pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Omie PDV"
include(":app")
include(":design_system")
include(":dashboard")
include(":domain")
include(":order_screen")
include(":utils")
include(":navigation")
include(":core")
