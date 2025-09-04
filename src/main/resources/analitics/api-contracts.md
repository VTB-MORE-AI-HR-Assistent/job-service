# VTB AI HR Assistant - Backend API Contracts

## Overview
This document defines the complete API contract between the frontend React/Next.js application and the Kotlin Spring Boot backend services.

## Base Configuration

### API Base URL
- Development: `http://localhost:8080`
- Staging: `https://api-staging.vtbaihr.com`
- Production: `https://api.vtbaihr.com`

### API Version
All endpoints are prefixed with `/api/v1`

### Authentication
- Type: JWT Bearer Token
- Header: `Authorization: Bearer <token>`
- Token expiry: 1 hour (access token), 7 days (refresh token)

### Common Headers
```
Content-Type: application/json
Accept: application/json
X-Request-ID: <uuid> (optional, for tracing)
```

### Common Error Response Format
```json
{
  "timestamp": "2025-01-09T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/endpoint",
  "details": {
    "field": "email",
    "rejectedValue": "invalid-email",
    "message": "Must be a valid email address"
  }
}
```

### HTTP Status Codes
- `200 OK` - Success
- `201 Created` - Resource created
- `204 No Content` - Success with no response body
- `400 Bad Request` - Validation error
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Access denied
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource already exists
- `422 Unprocessable Entity` - Business logic error
- `500 Internal Server Error` - Server error

## API Domains

### 1. Authentication & User Management
- `/api/v1/auth/*` - Authentication endpoints
- `/api/v1/users/*` - User profile management

### 2. HR Management
- `/api/v1/vacancies/*` - Job vacancies CRUD
- `/api/v1/candidates/*` - Candidate management
- `/api/v1/resumes/*` - Resume processing
- `/api/v1/questions/*` - Question bank management

### 3. Interview Management
- `/api/v1/interviews/*` - Interview sessions
- `/api/v1/interview-results/*` - Interview outcomes
- `/api/v1/interview-reports/*` - AI-generated reports

### 4. Analytics & Reports
- `/api/v1/analytics/*` - Dashboard analytics
- `/api/v1/reports/*` - Report generation

### 5. Daily.co Integration
- `/api/v1/daily/*` - Video room management

### 6. AI Services Integration
- `/api/v1/ai/*` - AI processing endpoints

## Pagination
For list endpoints, use standard pagination parameters:
```
?page=0&size=20&sort=createdAt,desc
```

Response includes pagination metadata:
```json
{
  "content": [...],
  "totalElements": 100,
  "totalPages": 5,
  "size": 20,
  "number": 0,
  "first": true,
  "last": false,
  "numberOfElements": 20
}
```

## Filtering
List endpoints support filtering via query parameters:
```
GET /api/v1/vacancies?status=ACTIVE&department=Engineering&location=Moscow
```

## Sorting
Use `sort` parameter with field name and direction:
```
?sort=createdAt,desc
?sort=matchScore,asc&sort=name,asc
```

## Rate Limiting
- 100 requests per minute per authenticated user
- 20 requests per minute for unauthenticated endpoints
- Headers returned:
  - `X-RateLimit-Limit: 100`
  - `X-RateLimit-Remaining: 95`
  - `X-RateLimit-Reset: 1704796800`

## WebSocket Endpoints
For real-time features:
- `/ws/interviews` - Interview session updates
- `/ws/notifications` - Real-time notifications

## File Upload
- Max file size: 10MB
- Supported formats: PDF, DOCX, DOC, TXT
- Endpoint: `POST /api/v1/files/upload`
- Content-Type: `multipart/form-data`

## Health Check
```
GET /api/v1/health
Response: { "status": "UP", "timestamp": "2025-01-09T10:30:00Z" }
```

## API Versioning Strategy
- Version in URL path: `/api/v1`, `/api/v2`
- Breaking changes require new version
- Deprecation notice: 6 months
- Sunset period: 12 months

## CORS Configuration
Allowed origins:
- Development: `http://localhost:3000`, `http://localhost:3001`
- Production: `https://vtbaihr.com`, `https://www.vtbaihr.com`

Allowed methods: `GET, POST, PUT, DELETE, OPTIONS, PATCH`
Allowed headers: `Authorization, Content-Type, X-Request-ID`