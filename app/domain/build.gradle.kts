import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.jpa") version "1.9.22"
}

allprojects {

    apply {
        plugin("kotlin-jpa")
    }

    dependencies {
        implementation("com.github.f4b6a3:ulid-creator:4.1.1")

        // jpa
        implementation("org.springframework.data:spring-data-commons")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-jdbc")

        // db
        runtimeOnly("com.h2database:h2")
        runtimeOnly("org.postgresql:postgresql")

        // persistence
        kapt("jakarta.persistence:jakarta.persistence-api")
        kapt("jakarta.annotation:jakarta.annotation-api")

        // querydsl
        implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    }

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
        annotation("com.domain.global.annotation.AllOpen")
    }

    tasks.withType<BootJar> {
        enabled = false
    }
}
