plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "com.aristurtle"
version = "0.0.1-SNAPSHOT"
description = "AI HR job and vacancy service"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // Spring AI file parsing
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")
	implementation("org.springframework.ai:spring-ai-starter-model-openai")
    // Для DOCX
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    // Для различных форматов через Tika
    implementation("org.springframework.ai:spring-ai-tika-document-reader")
    implementation("org.apache.tika:tika-core:2.7.0")
    // Для Spring Boot 6.2.10 используйте SpringDoc 2.2.0
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    runtimeOnly("org.postgresql:postgresql")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.modelmapper:modelmapper:3.2.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.test {
    useJUnitPlatform()
}