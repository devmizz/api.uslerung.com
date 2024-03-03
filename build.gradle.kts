import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.0"
}

allprojects {
    group = "com"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("java")
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    dependencies {
        implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation(kotlin("stdlib-jdk8"))

        testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

        // kotest
        testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
        testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
        testImplementation("io.kotest:kotest-assertions-core:5.5.5")
        testImplementation("io.kotest:kotest-property:5.5.5")

        // mock
        testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
        testImplementation("org.mockito:mockito-inline:5.2.0")
        testImplementation("io.mockk:mockk:1.13.4")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

val installGitPreCommitHook = tasks.register<Copy>("installGitPreCommitHook") {
    println("=== Register Git Pre-Commit Hook Start ===")

    exec {
        commandLine("cp", "-f", "${project.rootDir}/scripts/pre-commit", "${project.rootDir}/.git/hooks")
    }

    println("> Update File Permission")

    exec {
        commandLine("chmod", "+x", "${project.rootDir}/.git/hooks/pre-commit")
    }

    println("=== Register Git Pre-Commit Hook End ===")
}
