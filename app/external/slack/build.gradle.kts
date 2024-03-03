dependencies {
    // project dependency
    implementation(project(":app:application:user-application"))
    implementation(project(":app:application:schedule-application"))

    // spring-web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // slack api
    implementation("com.slack.api:slack-api-model-kotlin-extension:1.36.1")
    implementation("com.slack.api:slack-api-client-kotlin-extension:1.36.1")

    // slack bolt
    implementation("com.slack.api:bolt:1.38.0")
}
