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

## 🚀 API Endpoints

### Основные endpoints

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/v1/vacancies` | Получить все вакансии |
| `GET` | `/api/v1/vacancies/{id}` | Получить вакансию по ID |
| `POST` | `/api/v1/vacancies` | Создать новую вакансию |
| `PUT` | `/api/v1/vacancies/{id}` | Обновить вакансию |
| `DELETE` | `/api/v1/vacancies/{id}` | Удалить вакансию |
| `GET` | `/api/v1/vacancies/search` | Поиск вакансий с фильтрами |

### Поиск и фильтрация

Поиск поддерживает следующие параметры:
- `title` - поиск по названию вакансии
- `company` - поиск по компании
- `location` - поиск по местоположению
- `isRemote` - удаленная работа (true/false)

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

### Структура тестов

- **Unit тесты** - тестирование отдельных компонентов
- **Интеграционные тесты** - тестирование API endpoints
- **Валидационные тесты** - тестирование валидации данных

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