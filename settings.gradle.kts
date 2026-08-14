import org.gradle.api.initialization.resolve.RepositoriesMode

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

rootProject.name = "Luvoq"

include(":app")
include(":core:model")
include(":core:data")
include(":core:ui")
include(":automation")
include(":feature:home")
include(":feature:editor")
include(":feature:settings")
include(":feature:paywall")
