# Job Service 🚀

RESTful API для управления вакансиями с полным CRUD функционалом, написанный на Kotlin с использованием Spring Boot.

## 📋 О проекте

Это микросервис для управления вакансиями, который предоставляет:
- Создание, чтение, обновление и удаление вакансий
- Поиск и фильтрация вакансий по различным критериям
- Валидация данных и обработка ошибок
- Полная документация API через Swagger/OpenAPI
- Готовность к развертыванию в Docker контейнерах

## 🛠 Технологический стек

- **Kotlin** 1.9+ - Язык программирования
- **Spring Boot** 3.2+ - Фреймворк
- **Spring Data JPA** - Работа с базой данных
- **PostgreSQL** - Реляционная база данных
- **Springdoc OpenAPI** - Документация API
- **Gradle** - Система сборки
- **Docker** - Контейнеризация
- **JUnit 5 & Mockito** - Тестирование

## 📦 Быстрый старт

### Предварительные требования

- JDK 17 или выше
- Docker и Docker Compose
- PostgreSQL (если запускается без Docker)

### Запуск с Docker Compose

1. Клонируйте репозиторий:
```bash
git clone <repository-url>
cd job-service
```

2. Запустите приложение:
```bash
docker-compose up -d
```

Приложение будет доступно по адресу: http://localhost:8585

### Ручная установка

1. Соберите приложение:
```bash
./gradlew build
```

2. Запустите приложение:
```bash
./gradlew bootRun
```

3. Настройте базу данных в `application.yml`

**Пример запроса поиска:**
```bash
GET /api/v1/vacancies/search?title=Java&isRemote=true
```

## 📖 Документация API

### Swagger UI

После запуска приложения документация доступна по адресу:
- **Swagger UI:** http://localhost:8585/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8585/api-docs
- **OpenAPI YAML:** http://localhost:8585/api-docs.yaml

### DTO
```kotlin
data class VacancyRequest(
    val status: String? = null,
    val region: String? = null,
    val city: String? = null,
    val address: String? = null,
    val workType: String? = null,
    val employmentType: String? = null,
    val income: Int? = null,
    val salaryMax: Int? = null,
    val salaryMin: Int? = null,
    val workSchedule: String? = null,
    val annualBonus: Int? = null,
    val bonusType: String? = null,
    val responsibilities: List<String> = emptyList(),
    val requirements: List<String> = emptyList(),
    val educationType: String? = null,
    val experienceFrom: Int? = null,
    val experienceTo: Int? = null,
    val knowledgeLanguages: List<String> = emptyList(),
    val levelLanguages: List<String> = emptyList(),
    val businessTrips: Boolean? = null,
    val additionalInfo: String? = null,
    val programRequirements: List<String> = emptyList()
)
```

```kotlin
data class VacancySearchCriteria(
    val status: String? = null,
    val region: String? = null,
    val city: String? = null,
    val address: String? = null,
    val workType: String? = null,
    val employmentType: String? = null,
    val income: Int? = null,
    val salaryMax: Int? = null,
    val salaryMin: Int? = null,
    val workSchedule: String? = null,
    val annualBonus: Int? = null,
    val bonusType: String? = null,
    val responsibilities: List<String> = emptyList(),
    val requirements: List<String> = emptyList(),
    val educationType: String? = null,
    val experienceFrom: Int? = null,
    val experienceTo: Int? = null,
    val knowledgeLanguages: List<String> = emptyList(),
    val levelLanguages: List<String> = emptyList(),
    val businessTrips: Boolean? = null,
    val additionalInfo: String? = null,
    val programRequirements: List<String> = emptyList()
)
```

### Model
```kotlin
@Entity
@Table(name = "vacancies", schema = "jobs")
data class Vacancy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "status", length = 50)
    val status: String? = null,

    @Column(name = "region", length = 100)
    val region: String? = null,

    @Column(name = "city", length = 100)
    val city: String? = null,

    @Column(name = "address", length = 255)
    val address: String? = null,

    @Column(name = "work_type", length = 100)
    val workType: String? = null,

    @Column(name = "position_title")
    val positionTitle: String? = null,

    @Column(name = "employment_type", length = 100)
    val employmentType: String? = null,

    @Column(name = "income")
    val income: Int? = null,

    @Column(name = "salary_max")
    val salaryMax: Int? = null,

    @Column(name = "salary_min")
    val salaryMin: Int? = null,

    @Column(name = "work_schedule", length = 100)
    val workSchedule: String? = null,

    @Column(name = "annual_bonus")
    val annualBonus: Int? = null,

    @Column(name = "bonus_type", length = 50)
    val bonusType: String? = null,

    @Column(name = "responsibilities", columnDefinition = "TEXT[]")
    val responsibilities: List<String> = emptyList(),

    @Column(name = "requirements", columnDefinition = "TEXT[]")
    val requirements: List<String> = emptyList(),

    @Column(name = "education_type", length = 100)
    val educationType: String? = null,

    @Column(name = "experience_from")
    val experienceFrom: Int? = null,

    @Column(name = "experience_to")
    val experienceTo: Int? = null,

    @Column(name = "knowledge_languages", columnDefinition = "VARCHAR(50)[]")
    val knowledgeLanguages: List<String> = emptyList(),

    @Column(name = "level_languages", columnDefinition = "VARCHAR(50)[]")
    val levelLanguages: List<String> = emptyList(),

    @Column(name = "business_trips")
    val businessTrips: Boolean? = null,

    @Column(name = "additional_info", columnDefinition = "TEXT")
    val additionalInfo: String? = null,

    @Column(name = "program_requirements", columnDefinition = "TEXT[]")
    val programRequirements: List<String> = emptyList(),

    @Column(name = "creation_dts", nullable = false)
    val creationDts: OffsetDateTime = OffsetDateTime.now()
)
```

## 🧪 Тестирование

### Запуск тестов

```bash
# Все тесты
./gradlew test

# Только unit тесты
./gradlew test --tests "*Test"

# С генерацией отчета
./gradlew test --info

# Запуск с покрытием кода
./gradlew test jacocoTestReport
```

## 🐳 Docker

### Сборка образа

```bash
docker build -t job-service .
```

### Запуск контейнера

```bash
docker run -d \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/ai_hr_db \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=1234 \
  job-service
```

### Docker Compose

Полный стек с PostgreSQL и pgAdmin:
```bash
docker-compose up -d
```

## 📊 База данных

### Структура таблиц
*Общее*
```sql
CREATE SCHEMA IF NOT EXISTS jobs;
```

*Vacancies*
```sql
CREATE TABLE jobs.vacancies (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50),
    region VARCHAR(100),
    city VARCHAR(100),
    address VARCHAR(255),
    position_title varchar,
    work_type VARCHAR(100),
    employment_type VARCHAR(100),
    income INTEGER,
    salary_max INTEGER,
    salary_min INTEGER,
    work_schedule VARCHAR(100),
    annual_bonus INTEGER,
    bonus_type VARCHAR(50),
    responsibilities TEXT[],
    requirements TEXT[],
    education_type VARCHAR(100),
    experience_from INTEGER,
    experience_to INTEGER,
    knowledge_languages VARCHAR(50)[],
    level_languages VARCHAR(50)[],
    business_trips BOOLEAN,
    additional_info TEXT,
    program_requirements TEXT[],
    creation_dts TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
```