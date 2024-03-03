import org.springframework.boot.gradle.tasks.bundling.BootJar

allprojects {
    dependencies {
        implementation(project(":app:domain"))

        implementation("org.springframework.data:spring-data-commons")
    }
}

allprojects {
    tasks.withType<BootJar> {
        enabled = false
    }
}
