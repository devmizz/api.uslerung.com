import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("kotlin-kapt")

    kotlin("plugin.spring") version "1.9.21"
//    kotlin("kapt")
}

allprojects {

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("kotlin-spring")
        plugin("kotlin-kapt")
    }

    dependencies {
        implementation(project(":common"))

        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        // logging
        implementation("io.github.microutils:kotlin-logging:3.0.5")

        // spring test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
        testImplementation("com.ninja-squad:springmockk:4.0.2")
    }
}

tasks.withType<BootJar> {
    enabled = false
}
