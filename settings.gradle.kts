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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AndroidLab"
include(":app")
include(":ch12_material")
include(":ch13_activity")
include(":ch14_broad")
include(":ch15_outer")
include(":ch15_service")
include(":ex_thread_n_corutine")
include(":ch15_corutine")
include(":servicetest2")
include(":servicetest2")
include(":foregroundservice")
include(":jobschedulertest")
include(":ch15_musicplayer")
include(":ch15_musicplayer2")
include(":ch16_provider")
