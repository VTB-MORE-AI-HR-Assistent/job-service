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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("jakarta.validation:jakarta.validation-api:3.1.1")
	implementation("org.modelmapper:modelmapper:3.2.3")
	// Spring AI
	implementation("org.springframework.ai:spring-ai-client-chat:1.0.1")
	implementation("org.springframework.ai:spring-ai-openai")
	// PDF обработка
	implementation("org.apache.pdfbox:pdfbox:3.0.1")
	implementation("org.apache.tika:tika-core:2.9.1")
	// Для работы с файлами
	implementation("org.springframework.boot:spring-boot-starter-web")
	// Кэширование
	implementation("org.springframework.boot:spring-boot-starter-cache")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	testImplementation("org.hibernate.validator:hibernate-validator")
	testImplementation("org.glassfish:jakarta.el:4.0.2") // для валидации в тестах
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.mockito", module = "mockito-core")
	}
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
	testImplementation("com.ninja-squad:springmockk:4.0.2") // Альтернатива Mockito для Kotlin
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