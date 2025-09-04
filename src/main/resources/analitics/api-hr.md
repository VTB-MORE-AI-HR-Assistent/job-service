# HR Domain API Documentation

## Vacancy Management

### 1. Create Vacancy
```http
POST /api/v1/vacancies
```

**Headers:**
```
Authorization: Bearer <access_token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "Senior Frontend Developer",
  "department": "Engineering",
  "location": "Moscow",
  "type": "FULL_TIME",
  "description": "We are looking for an experienced Frontend Developer...",
  "requirements": [
    "5+ years of experience with React",
    "Strong TypeScript skills",
    "Experience with modern CSS frameworks"
  ],
  "responsibilities": [
    "Develop and maintain web applications",
    "Collaborate with design and backend teams",
    "Code review and mentoring"
  ],
  "skills": ["React", "TypeScript", "Next.js", "Redux", "GraphQL"],
  "experience": "SENIOR",
  "salary": {
    "min": 200000,
    "max": 300000,
    "currency": "RUB",
    "period": "MONTHLY"
  },
  "benefits": [
    "Health insurance",
    "Remote work option",
    "Professional development budget"
  ]
}
```

**Response (201 Created):**
```json
{
  "id": 123,
  "title": "Senior Frontend Developer",
  "department": "Engineering",
  "location": "Moscow",
  "type": "FULL_TIME",
  "status": "DRAFT",
  "description": "We are looking for an experienced Frontend Developer...",
  "requirements": [...],
  "responsibilities": [...],
  "skills": [...],
  "experience": "SENIOR",
  "salary": {...},
  "benefits": [...],
  "applicantsCount": 0,
  "interviewedCount": 0,
  "hiredCount": 0,
  "createdBy": 1,
  "createdAt": "2025-01-09T10:30:00Z",
  "updatedAt": "2025-01-09T10:30:00Z"
}
```

### 2. Get Vacancy
```http
GET /api/v1/vacancies/{id}
```

**Response (200 OK):** Vacancy object

### 3. Update Vacancy
```http
PUT /api/v1/vacancies/{id}
```

**Request Body:** UpdateVacancyRequest (partial update)

**Response (200 OK):** Updated vacancy object

### 4. Delete Vacancy
```http
DELETE /api/v1/vacancies/{id}
```

**Response (204 No Content)**

### 5. List Vacancies
```http
GET /api/v1/vacancies?status=ACTIVE&department=Engineering&page=0&size=20
```

**Query Parameters:**
- `status` - Filter by status (DRAFT, ACTIVE, PAUSED, CLOSED)
- `department` - Filter by department
- `location` - Filter by location
- `type` - Filter by employment type
- `experience` - Filter by experience level
- `search` - Search in title and description
- `page`, `size`, `sort` - Pagination

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 123,
      "title": "Senior Frontend Developer",
      "department": "Engineering",
      "location": "Moscow",
      "type": "FULL_TIME",
      "status": "ACTIVE",
      "applicantsCount": 47,
      "interviewedCount": 12,
      "createdAt": "2025-01-09T10:30:00Z"
    }
  ],
  "totalElements": 25,
  "totalPages": 2,
  "size": 20,
  "number": 0
}
```

### 6. Publish Vacancy
```http
POST /api/v1/vacancies/{id}/publish
```

**Response (200 OK):**
```json
{
  "id": 123,
  "status": "ACTIVE",
  "publishedAt": "2025-01-09T11:00:00Z"
}
```

### 7. Close Vacancy
```http
POST /api/v1/vacancies/{id}/close
```

**Request Body:**
```json
{
  "reason": "Position filled",
  "hiredCandidateId": 456
}
```

**Response (200 OK):**
```json
{
  "id": 123,
  "status": "CLOSED",
  "closedAt": "2025-01-09T11:00:00Z"
}
```

### 8. Get Vacancy Analytics
```http
GET /api/v1/vacancies/{id}/analytics
```

**Response (200 OK):**
```json
{
  "vacancyId": 123,
  "title": "Senior Frontend Developer",
  "totalApplicants": 47,
  "qualifiedApplicants": 32,
  "interviewedCandidates": 12,
  "offersExtended": 3,
  "offersAccepted": 1,
  "averageMatchScore": 78.5,
  "averageInterviewScore": 82.3,
  "timeToFill": 21,
  "sourceBreakdown": {
    "DIRECT": 20,
    "LINKEDIN": 15,
    "REFERRAL": 8,
    "JOB_BOARD": 4
  },
  "candidatePipeline": [
    {
      "stage": "NEW",
      "count": 15,
      "percentage": 31.9
    },
    {
      "stage": "SCREENING",
      "count": 20,
      "percentage": 42.6
    }
  ]
}
```

## Candidate Management

### 9. Create Candidate
```http
POST /api/v1/candidates
```

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+7 999 123-45-67",
  "linkedIn": "linkedin.com/in/johndoe",
  "currentPosition": "Senior Developer",
  "currentCompany": "Tech Corp",
  "experience": 5,
  "skills": ["React", "TypeScript", "Node.js"],
  "location": "Moscow",
  "relocationReady": true,
  "expectedSalary": {
    "min": 180000,
    "max": 250000,
    "currency": "RUB"
  },
  "vacancyId": 123,
  "source": "LINKEDIN"
}
```

**Response (201 Created):** Created candidate object

### 10. Get Candidate
```http
GET /api/v1/candidates/{id}
```

**Response (200 OK):** Full candidate object with all details

### 11. Update Candidate
```http
PUT /api/v1/candidates/{id}
```

**Request Body:** Partial update with any candidate fields

**Response (200 OK):** Updated candidate object

### 12. Delete Candidate
```http
DELETE /api/v1/candidates/{id}
```

**Response (204 No Content)**

### 13. List Candidates
```http
GET /api/v1/candidates?vacancyId=123&status=SCREENING&page=0&size=20
```

**Query Parameters:**
- `vacancyId` - Filter by vacancy
- `status` - Filter by candidate status
- `minScore` - Minimum match score
- `skills` - Filter by skills (comma-separated)
- `experience` - Minimum years of experience
- `search` - Search in name, email, skills
- Pagination parameters

**Response (200 OK):** Paginated list of candidates

### 14. Update Candidate Status
```http
PUT /api/v1/candidates/{id}/status
```

**Request Body:**
```json
{
  "status": "INTERVIEW_SCHEDULED",
  "notes": "Scheduled for technical interview on Monday"
}
```

**Response (200 OK):** Updated candidate with new status

### 15. Bulk Import Candidates
```http
POST /api/v1/candidates/bulk-import
```

**Request Body:**
```json
{
  "vacancyId": 123,
  "source": "LINKEDIN",
  "candidates": [
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "skills": ["React", "TypeScript"]
    }
  ]
}
```

**Response (201 Created):**
```json
{
  "imported": 10,
  "failed": 2,
  "errors": [
    {
      "row": 3,
      "email": "invalid@",
      "error": "Invalid email format"
    }
  ]
}
```

## Resume Processing

### 16. Upload Resume
```http
POST /api/v1/resumes/upload
```

**Headers:**
```
Authorization: Bearer <access_token>
Content-Type: multipart/form-data
```

**Form Data:**
- `file` - Resume file (PDF, DOCX, DOC)
- `candidateId` - Optional candidate ID
- `vacancyId` - Optional vacancy ID for matching

**Response (201 Created):**
```json
{
  "resumeId": 789,
  "fileName": "john_doe_resume.pdf",
  "fileUrl": "/api/v1/resumes/789/download",
  "uploadedAt": "2025-01-09T10:30:00Z",
  "status": "PROCESSING"
}
```

### 17. Get Resume Analysis
```http
GET /api/v1/resumes/{resumeId}/analysis
```

**Response (200 OK):**
```json
{
  "resumeId": 789,
  "candidateId": 456,
  "extractedData": {
    "personalInfo": {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "phone": "+7 999 123-45-67",
      "location": "Moscow"
    },
    "summary": "Experienced developer with...",
    "skills": ["React", "TypeScript", "Node.js"],
    "experience": [
      {
        "company": "Tech Corp",
        "position": "Senior Developer",
        "startDate": "2020-01-01",
        "endDate": null,
        "isOngoing": true
      }
    ],
    "education": [
      {
        "institution": "Moscow State University",
        "degree": "Master's",
        "field": "Computer Science",
        "endYear": 2018
      }
    ]
  },
  "matchScore": 85.5,
  "skillsMatch": [
    {
      "skill": "React",
      "required": true,
      "found": true,
      "proficiency": "EXPERT"
    }
  ],
  "overallAssessment": "Strong candidate with excellent technical skills...",
  "strengths": [
    "Strong React experience",
    "Good educational background"
  ],
  "gaps": [
    "Limited backend experience"
  ],
  "processedAt": "2025-01-09T10:31:00Z"
}
```

### 18. Parse Resume Text
```http
POST /api/v1/resumes/parse
```

**Request Body:**
```json
{
  "resumeText": "John Doe\nSenior Developer\n...",
  "vacancyId": 123
}
```

**Response (200 OK):** Extracted resume data and match analysis

### 19. Download Resume
```http
GET /api/v1/resumes/{resumeId}/download
```

**Response (200 OK):**
```
Content-Type: application/pdf
Content-Disposition: attachment; filename="resume.pdf"

[Binary file data]
```

## Question Bank Management

### 20. Create Question
```http
POST /api/v1/questions
```

**Request Body:**
```json
{
  "question": "What is the difference between let and const in JavaScript?",
  "category": "TECHNICAL",
  "subcategory": "JavaScript",
  "type": "OPEN_ENDED",
  "difficulty": "MEDIUM",
  "skills": ["JavaScript", "ES6"],
  "expectedAnswerPoints": [
    "let allows reassignment, const doesn't",
    "Both are block-scoped",
    "const requires initialization"
  ],
  "followUpQuestions": [
    "Can you give an example?",
    "What about var?"
  ],
  "timeLimit": 180,
  "tags": ["frontend", "basics"]
}
```

**Response (201 Created):** Created question object

### 21. Get Question
```http
GET /api/v1/questions/{id}
```

**Response (200 OK):** Question object

### 22. Update Question
```http
PUT /api/v1/questions/{id}
```

**Request Body:** Partial update of question fields

**Response (200 OK):** Updated question

### 23. Delete Question
```http
DELETE /api/v1/questions/{id}
```

**Response (204 No Content)**

### 24. List Questions
```http
GET /api/v1/questions?category=TECHNICAL&difficulty=MEDIUM&skills=React,TypeScript
```

**Query Parameters:**
- `category` - Filter by category
- `difficulty` - Filter by difficulty
- `skills` - Filter by skills (comma-separated)
- `tags` - Filter by tags
- `isActive` - Filter active/inactive
- `search` - Search in question text
- Pagination parameters

**Response (200 OK):** Paginated list of questions

### 25. Get Questions for Interview
```http
GET /api/v1/questions/interview-set
```

**Query Parameters:**
- `vacancyId` - Vacancy to match questions
- `difficulty` - Interview difficulty
- `count` - Number of questions (default: 10)
- `categories` - Categories to include

**Response (200 OK):**
```json
{
  "questions": [
    {
      "id": 1,
      "question": "Tell me about yourself",
      "category": "GENERAL",
      "timeLimit": 180,
      "order": 1
    }
  ],
  "totalDuration": 1800,
  "categories": {
    "GENERAL": 2,
    "TECHNICAL": 5,
    "BEHAVIORAL": 3
  }
}
```

## Reports and Analytics

### 26. Generate Interview Report
```http
POST /api/v1/reports/interview
```

**Request Body:**
```json
{
  "interviewId": 123,
  "format": "PDF",
  "includeTranscript": true,
  "includeAnalytics": true,
  "includeRecommendations": true
}
```

**Response (200 OK):**
```json
{
  "reportId": "report-123",
  "status": "GENERATING",
  "estimatedTime": 30,
  "downloadUrl": null
}
```

### 27. Get Report Status
```http
GET /api/v1/reports/{reportId}/status
```

**Response (200 OK):**
```json
{
  "reportId": "report-123",
  "status": "COMPLETED",
  "downloadUrl": "/api/v1/reports/report-123/download",
  "generatedAt": "2025-01-09T10:35:00Z"
}
```

### 28. Download Report
```http
GET /api/v1/reports/{reportId}/download
```

**Response (200 OK):** Binary file data

### 29. Get Dashboard Analytics
```http
GET /api/v1/analytics/dashboard
```

**Query Parameters:**
- `fromDate` - Start date for metrics
- `toDate` - End date for metrics
- `department` - Filter by department

**Response (200 OK):**
```json
{
  "totalVacancies": 15,
  "activeVacancies": 10,
  "totalCandidates": 234,
  "newCandidatesThisWeek": 45,
  "totalInterviews": 89,
  "scheduledInterviews": 12,
  "completedInterviews": 77,
  "averageMatchScore": 76.8,
  "averageInterviewScore": 78.5,
  "hiringRate": 0.15,
  "timeToHire": 18.5,
  "topSkillsInDemand": [
    {
      "skill": "React",
      "demand": 45,
      "candidates": 120
    }
  ],
  "departmentMetrics": [
    {
      "department": "Engineering",
      "vacancies": 8,
      "candidates": 156,
      "interviews": 45,
      "hires": 5
    }
  ],
  "weeklyTrends": [
    {
      "week": "2025-W01",
      "applications": 45,
      "interviews": 12,
      "hires": 2
    }
  ],
  "conversionFunnel": {
    "applied": 234,
    "screened": 156,
    "interviewed": 89,
    "offered": 23,
    "hired": 15
  }
}
```

### 30. Get Vacancy Pipeline
```http
GET /api/v1/analytics/vacancy/{vacancyId}/pipeline
```

**Response (200 OK):**
```json
{
  "vacancyId": 123,
  "stages": [
    {
      "stage": "APPLIED",
      "count": 47,
      "percentage": 100,
      "averageTimeInStage": 2.5
    },
    {
      "stage": "SCREENING",
      "count": 32,
      "percentage": 68.1,
      "averageTimeInStage": 3.2
    },
    {
      "stage": "INTERVIEW",
      "count": 12,
      "percentage": 25.5,
      "averageTimeInStage": 5.8
    },
    {
      "stage": "OFFER",
      "count": 3,
      "percentage": 6.4,
      "averageTimeInStage": 2.1
    },
    {
      "stage": "HIRED",
      "count": 1,
      "percentage": 2.1,
      "averageTimeInStage": null
    }
  ],
  "dropOffReasons": {
    "NOT_QUALIFIED": 15,
    "WITHDREW": 8,
    "FAILED_INTERVIEW": 9,
    "DECLINED_OFFER": 2
  }
}
```

### 31. Export Candidates
```http
GET /api/v1/candidates/export
```

**Query Parameters:**
- `vacancyId` - Filter by vacancy
- `status` - Filter by status
- `format` - Export format (CSV, XLSX, JSON)

**Response (200 OK):**
```
Content-Type: text/csv
Content-Disposition: attachment; filename="candidates-export.csv"

"First Name","Last Name","Email","Status","Score"
"John","Doe","john@example.com","SCREENING","85.5"
```

## Notifications

### 32. Send Interview Invitation
```http
POST /api/v1/notifications/interview-invitation
```

**Request Body:**
```json
{
  "candidateId": 456,
  "interviewId": 789,
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "template": "INTERVIEW_INVITATION",
  "language": "ru"
}
```

**Response (200 OK):**
```json
{
  "notificationId": "notif-123",
  "status": "SENT",
  "sentAt": "2025-01-09T10:30:00Z",
  "channel": "EMAIL"
}
```

### 33. Send Bulk Notifications
```http
POST /api/v1/notifications/bulk
```

**Request Body:**
```json
{
  "recipientIds": [1, 2, 3],
  "type": "VACANCY_UPDATE",
  "subject": "New vacancy available",
  "message": "Check out our new Senior Developer position",
  "channels": ["EMAIL", "SMS"]
}
```

**Response (200 OK):**
```json
{
  "sent": 3,
  "failed": 0,
  "details": [
    {
      "recipientId": 1,
      "status": "SENT",
      "channel": "EMAIL"
    }
  ]
}
```