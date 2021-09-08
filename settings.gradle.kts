
rootProject.name = "test-library"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
            }
            if (requested.id.id == "org.jetbrains.kotlin.kapt") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
            }
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:1.5.0")
            }
        }
    }

    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven("https://cache-redirector.jetbrains.com/maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        mavenLocal()
        // ... other repos
        maven("https://maven.pkg.jetbrains.space/public/p/jb-coverage/maven")
    }
}
