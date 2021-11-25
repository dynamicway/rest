import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.31"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.6")
    implementation("org.springframework.boot:spring-boot-starter-hateoas:2.5.6")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("com.h2database:h2:1.4.200")
    runtimeOnly("org.postgresql:postgresql:42.3.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.6")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.5.RELEASE")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.6.3")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.3")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}