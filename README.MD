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

### Модели данных

#### VacancyRequest
```json
{
  "title": "Java Developer",
  "company": "TechCorp",
  "description": "Разработка backend на Java",
  "requirements": "Опыт работы с Spring Boot",
  "contacts": "hr@techcorp.com",
  "salary_from": 150000,
  "salary_to": 250000,
  "salary_currency": "RUB",
  "salary_gross": true,
  "experience": "от 3 лет",
  "employment_type": "полная",
  "work_schedule": "удаленная работа",
  "location": "Москва",
  "is_remote": true,
  "status": "active"
}
```

#### VacancyResponse
```json
{
  "id": 1,
  "title": "Java Developer",
  "company": "TechCorp",
  "description": "Разработка backend на Java",
  "salary_from": 150000,
  "salary_to": 250000,
  "salary_currency": "RUB",
  "is_remote": true,
  "status": "active",
  "created_at": "2024-01-15T10:30:00",
  "updated_at": "2024-01-15T10:30:00"
}
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

### Структура таблицы

```sql
CREATE TABLE jobs.vacancies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    description TEXT,
    requirements TEXT,
    contacts TEXT,
    salary_from INTEGER,
    salary_to INTEGER,
    salary_currency VARCHAR(10),
    salary_gross BOOLEAN,
    experience VARCHAR(50),
    employment_type VARCHAR(50),
    work_schedule VARCHAR(50),
    location VARCHAR(255),
    is_remote BOOLEAN DEFAULT FALSE,
    status VARCHAR(50) default 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🔒 Валидация

API включает комплексную валидацию:

- Обязательные поля: `title`, `company`
- Валидация зарплаты: `salary_from` ≤ `salary_to`
- Ограничение длины строковых полей

## 🤝 Разработка

### Структура проекта

```
src/
├── main/
│   ├── kotlin/com/example/vacancies/
│   │   ├── controller/    # REST контроллеры
│   │   ├── service/       # Бизнес-логика
│   │   ├── repository/    # Доступ к данным
│   │   ├── model/         # Модели данных
│   │   ├── dto/          # Data Transfer Objects
│   │   └── config/       # Конфигурация
│   └── resources/
│       └── application.yml
└── test/                 # Тесты
```

### Коммиты

Используйте Conventional Commits:
- `feat:` Новая функциональность
- `fix:` Исправление ошибок
- `docs:` Изменения в документации
- `test:` Добавление тестов
- `refactor:` Рефакторинг кода