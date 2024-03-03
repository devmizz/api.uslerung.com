import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
}

allprojects {
    tasks.withType<BootJar> {
        enabled = false
    }
}
