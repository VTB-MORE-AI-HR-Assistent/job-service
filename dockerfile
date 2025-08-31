# Базовый образ с JDK 17
FROM eclipse-temurin:17-jdk-jammy as builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы для определения зависимостей
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle.properties ./

# Делаем gradlew исполняемым
RUN chmod +x gradlew

# Копируем исходный код
COPY src src

# Собираем приложение с помощью Gradle
RUN ./gradlew build -x test --no-daemon

# Финальный образ
FROM eclipse-temurin:17-jre-jammy

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR из builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Создаем пользователя для безопасности
RUN groupadd -r spring && useradd -r -g spring spring \
    && chown -R spring:spring /app

USER spring

# Открываем порт
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/app.jar"]