plugins {
    kotlin("multiplatform") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
    id("maven-publish")
    id("org.jetbrains.kover-plugin") version "0.4"
}

group = "org.jetbrains.kotlinx"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}

afterEvaluate {
    tasks {
        named<Test>("jvmTest") {
            extensions.configure(org.jetbrains.kover.plugin.TestTaskExtension::class) {
//                includes += "kotlinx\\.serialization\\..*" // regexps for included classes, all classes if not specified
                includes += "org\\.jetbrains\\.kotlinx\\.test_library\\..*" // regexps for included classes, all classes if not specified
            }
//        enabled = true // false for disable coverage for this task

//        excludes += "com\\..*" // regexps for excluded classes, no exculded classes if not specified
        }
    }

}
