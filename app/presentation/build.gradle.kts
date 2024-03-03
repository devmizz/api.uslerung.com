dependencies {
    implementation(project(":app:application"))
    implementation(project(":app:application:user-application"))
    implementation(project(":app:application:schedule-application"))

    implementation(project(":app:external:slack"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    // SWAGGER-SPRINGDOC-OPENAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    // Thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // slack-servlet
    implementation("com.slack.api:bolt-jakarta-servlet:1.38.0")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // jwt
    implementation("com.auth0:java-jwt:4.3.0")
}
