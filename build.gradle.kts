import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application

    kotlin("jvm")
    kotlin("plugin.serialization")

    id("com.github.johnrengelman.shadow")
}

group = "org.ecorous.shork"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()

    maven {
        name = "Sonatype Snapshots (Legacy)"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }

    maven {
        name = "Sonatype Snapshots"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }

    maven("https://maven.proxyfox.dev")
}

dependencies {
    implementation(libs.kord.extensions)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kx.ser)

    // Logging dependencies
    implementation(libs.groovy)
    implementation(libs.jansi)
    implementation(libs.logback)
    implementation(libs.logging)

    implementation(libs.pluralkt)
}

application {
    // This is deprecated, but the Shadow plugin requires it
    mainClassName = "org.ecorous.shork.AppKt"
}

tasks.withType<KotlinCompile> {
    // Current LTS version of Java
    kotlinOptions.jvmTarget = "17"

    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "org.ecorous.shork.AppKt"
        )
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
