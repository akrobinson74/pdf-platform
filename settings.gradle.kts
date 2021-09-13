pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.21.0"
}

refreshVersions {
    enableBuildSrcLibs()
}

rootProject.name = "pdf-platform"
