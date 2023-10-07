plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlinx-serialization")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    val ktorVersion = "2.3.4"
    val coroutinesVersion = "1.7.3"
    val serializationVersion = "1.5.1"
    val koinVersion = "3.5.0"

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:${serializationVersion}")
                implementation("io.ktor:ktor-client-core:${ktorVersion}")
                implementation("io.ktor:ktor-client-json:${ktorVersion}")
                implementation("io.ktor:ktor-client-serialization:${ktorVersion}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${ktorVersion}")
                implementation("io.ktor:ktor-client-content-negotiation:${ktorVersion}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${serializationVersion}")
                implementation("io.ktor:ktor-client-logging:${ktorVersion}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
                implementation("io.insert-koin:koin-core:${koinVersion}")
                implementation("io.insert-koin:koin-test:${koinVersion}")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.9.0")
                api("io.ktor:ktor-client-okhttp:${ktorVersion}")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesVersion}")
                implementation("io.insert-koin:koin-android:${koinVersion}")
                implementation("io.insert-koin:koin-androidx-compose:${koinVersion}")
            }
        }
        val iosMain by getting {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.example.bookkmm"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
