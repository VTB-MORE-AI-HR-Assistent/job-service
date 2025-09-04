# Interview Management API Documentation

## Overview
APIs for managing interview sessions, AI interviews, Daily.co integration, and interview results.

## Interview Session Endpoints

### 1. Create Interview Session
```http
POST /api/v1/interviews
```

**Headers:**
```
Authorization: Bearer <access_token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "candidateId": 123,
  "vacancyId": 456,
  "scheduledTime": "2025-01-10T10:00:00Z",
  "duration": 30,
  "type": "TECHNICAL",
  "difficulty": "SENIOR"
}
```

**Response (201 Created):**
```json
{
  "id": 789,
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "candidateId": 123,
  "vacancyId": 456,
  "candidateName": "John Doe",
  "candidateEmail": "john.doe@example.com",
  "position": "Senior Frontend Developer",
  "department": "Engineering",
  "scheduledTime": "2025-01-10T10:00:00Z",
  "duration": 30,
  "type": "TECHNICAL",
  "difficulty": "SENIOR",
  "status": "SCHEDULED",
  "roomUrl": null,
  "roomToken": null,
  "createdAt": "2025-01-09T10:30:00Z",
  "updatedAt": "2025-01-09T10:30:00Z"
}
```

### 2. Get Interview Session
```http
GET /api/v1/interviews/{sessionId}
```

**Headers:**
```
Authorization: Bearer <access_token>
```

**Response (200 OK):**
```json
{
  "id": 789,
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "candidateId": 123,
  "vacancyId": 456,
  "candidateName": "John Doe",
  "candidateEmail": "john.doe@example.com",
  "position": "Senior Frontend Developer",
  "department": "Engineering",
  "scheduledTime": "2025-01-10T10:00:00Z",
  "duration": 30,
  "type": "TECHNICAL",
  "difficulty": "SENIOR",
  "status": "SCHEDULED",
  "roomUrl": "https://hraiassistant.daily.co/interview-550e8400",
  "roomToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "startedAt": null,
  "endedAt": null,
  "actualDuration": null,
  "recordingUrl": null,
  "transcriptUrl": null,
  "createdAt": "2025-01-09T10:30:00Z",
  "updatedAt": "2025-01-09T10:30:00Z"
}
```

### 3. List Interview Sessions
```http
GET /api/v1/interviews?candidateId=123&vacancyId=456&status=SCHEDULED&page=0&size=20
```

**Headers:**
```
Authorization: Bearer <access_token>
```

**Query Parameters:**
- `candidateId` (optional) - Filter by candidate
- `vacancyId` (optional) - Filter by vacancy
- `status` (optional) - Filter by status
- `fromDate` (optional) - Start date filter
- `toDate` (optional) - End date filter
- `page` - Page number (default: 0)
- `size` - Page size (default: 20)
- `sort` - Sort field and direction

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 789,
      "sessionId": "550e8400-e29b-41d4-a716-446655440000",
      "candidateName": "John Doe",
      "position": "Senior Frontend Developer",
      "scheduledTime": "2025-01-10T10:00:00Z",
      "duration": 30,
      "status": "SCHEDULED",
      "type": "TECHNICAL"
    }
  ],
  "totalElements": 50,
  "totalPages": 3,
  "size": 20,
  "number": 0,
  "first": true,
  "last": false
}
```

### 4. Update Interview Session
```http
PUT /api/v1/interviews/{sessionId}
```

**Request Body:**
```json
{
  "scheduledTime": "2025-01-11T14:00:00Z",
  "duration": 45,
  "type": "BEHAVIORAL",
  "difficulty": "MIDDLE"
}
```

**Response (200 OK):** Updated interview session object

### 5. Cancel Interview
```http
POST /api/v1/interviews/{sessionId}/cancel
```

**Request Body:**
```json
{
  "reason": "Candidate requested reschedule",
  "notifyCandidate": true
}
```

**Response (200 OK):**
```json
{
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "CANCELLED",
  "cancelledAt": "2025-01-09T11:00:00Z",
  "cancelReason": "Candidate requested reschedule"
}
```

## Interview Execution Endpoints

### 6. Validate Interview Session (Public)
```http
POST /api/v1/interviews/validate
```

**No Authorization Required**

**Request Body:**
```json
{
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (200 OK):**
```json
{
  "valid": true,
  "session": {
    "sessionId": "550e8400-e29b-41d4-a716-446655440000",
    "candidateName": "John Doe",
    "position": "Senior Frontend Developer",
    "scheduledTime": "2025-01-10T10:00:00Z",
    "duration": 30,
    "status": "SCHEDULED"
  },
  "timeUntilInterview": 1440,
  "canJoin": false,
  "message": "You can join 15 minutes before scheduled time"
}
```

### 7. Join Interview (Start Session)
```http
POST /api/v1/interviews/{sessionId}/join
```

**Request Body:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "deviceInfo": {
    "browser": "Chrome 120",
    "os": "Windows 11",
    "microphone": true,
    "camera": true
  }
}
```

**Response (200 OK):**
```json
{
  "roomUrl": "https://hraiassistant.daily.co/interview-550e8400",
  "roomToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresAt": "2025-01-10T11:30:00Z",
  "enableRecording": true,
  "enableTranscription": true,
  "aiAssistant": {
    "enabled": true,
    "avatarUrl": "https://api.vtbaihr.com/avatars/ai-interviewer.png",
    "voice": "professional-female",
    "language": "ru-RU"
  },
  "interviewQuestions": [
    {
      "id": 1,
      "question": "Расскажите о себе и вашем опыте",
      "category": "GENERAL",
      "timeLimit": 180
    }
  ]
}
```

### 8. End Interview Session
```http
POST /api/v1/interviews/{sessionId}/end
```

**Request Body:**
```json
{
  "actualDuration": 1800,
  "completionStatus": "COMPLETED",
  "technicalIssues": false,
  "candidateFeedback": "Great experience"
}
```

**Response (200 OK):**
```json
{
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "COMPLETED",
  "endedAt": "2025-01-10T10:30:00Z",
  "actualDuration": 1800,
  "nextSteps": "Your interview results will be reviewed within 24 hours"
}
```

### 9. Report Technical Issue
```http
POST /api/v1/interviews/{sessionId}/report-issue
```

**Request Body:**
```json
{
  "issueType": "AUDIO",
  "description": "Microphone not working",
  "timestamp": "2025-01-10T10:15:00Z",
  "userAgent": "Mozilla/5.0...",
  "screenshot": "base64_encoded_image"
}
```

**Response (201 Created):**
```json
{
  "ticketId": "ISSUE-20250110-001",
  "status": "OPEN",
  "message": "Technical support has been notified"
}
```

## Interview Results Endpoints

### 10. Get Interview Result
```http
GET /api/v1/interview-results/{sessionId}
```

**Response (200 OK):**
```json
{
  "id": 1234,
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "interviewId": 789,
  "overallScore": 85.5,
  "technicalScore": 88.0,
  "behavioralScore": 82.0,
  "culturalScore": 86.0,
  "communicationScore": 85.0,
  "strengths": [
    "Strong technical knowledge in React and TypeScript",
    "Clear communication skills",
    "Good problem-solving approach"
  ],
  "weaknesses": [
    "Limited experience with microservices",
    "Could improve on system design concepts"
  ],
  "recommendations": "Strong candidate for the position. Consider additional training on microservices architecture.",
  "aiSummary": "The candidate demonstrated excellent frontend development skills...",
  "detailedAnalysis": {
    "technical": {
      "score": 88.0,
      "details": "Excellent knowledge of React ecosystem..."
    },
    "behavioral": {
      "score": 82.0,
      "details": "Shows good teamwork and leadership potential..."
    }
  },
  "questionsAsked": [
    {
      "questionId": 1,
      "question": "Explain React hooks and their benefits",
      "category": "TECHNICAL",
      "candidateAnswer": "React hooks are functions that...",
      "score": 9.0,
      "aiAnalysis": "Comprehensive answer covering all key points"
    }
  ],
  "redFlags": [],
  "hiringRecommendation": "STRONGLY_RECOMMEND",
  "confidence": 0.92,
  "processedAt": "2025-01-10T11:00:00Z"
}
```

### 11. Update Interview Result Review
```http
PUT /api/v1/interview-results/{sessionId}/review
```

**Request Body:**
```json
{
  "reviewNotes": "Agreed with AI assessment. Proceed to offer stage.",
  "hiringDecision": "PROCEED",
  "additionalComments": "Schedule follow-up with CTO"
}
```

**Response (200 OK):** Updated result with review information

### 12. Generate Interview Report PDF
```http
GET /api/v1/interview-results/{sessionId}/report
```

**Query Parameters:**
- `format` - Report format (PDF, DOCX, HTML)
- `includeTranscript` - Include full transcript (boolean)
- `includeAnalytics` - Include detailed analytics (boolean)

**Response (200 OK):**
```
Content-Type: application/pdf
Content-Disposition: attachment; filename="interview-report-550e8400.pdf"

[Binary PDF data]
```

## Daily.co Room Management

### 13. Create Daily.co Room
```http
POST /api/v1/daily/rooms
```

**Request Body:**
```json
{
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "candidateName": "John Doe",
  "duration": 30,
  "enableRecording": true,
  "enableTranscription": true
}
```

**Response (201 Created):**
```json
{
  "roomUrl": "https://hraiassistant.daily.co/interview-550e8400",
  "roomName": "interview-550e8400",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresAt": "2025-01-10T12:00:00Z",
  "properties": {
    "enable_recording": true,
    "enable_chat": true,
    "enable_screenshare": true,
    "max_participants": 2,
    "lang": "ru"
  }
}
```

### 14. Get Daily.co Room Status
```http
GET /api/v1/daily/rooms/{roomName}
```

**Response (200 OK):**
```json
{
  "roomName": "interview-550e8400",
  "url": "https://hraiassistant.daily.co/interview-550e8400",
  "isActive": true,
  "participants": [
    {
      "userId": "candidate-123",
      "userName": "John Doe",
      "joinedAt": "2025-01-10T10:00:00Z",
      "audioEnabled": true,
      "videoEnabled": false
    },
    {
      "userId": "ai-assistant",
      "userName": "AI Interviewer",
      "joinedAt": "2025-01-10T10:00:05Z",
      "audioEnabled": true,
      "videoEnabled": true
    }
  ],
  "recordingStatus": "RECORDING",
  "createdAt": "2025-01-10T09:45:00Z"
}
```

### 15. Delete Daily.co Room
```http
DELETE /api/v1/daily/rooms/{roomName}
```

**Response (204 No Content)**

## Interview Analytics

### 16. Get Interview Statistics
```http
GET /api/v1/interviews/statistics
```

**Query Parameters:**
- `vacancyId` - Filter by vacancy
- `fromDate` - Start date
- `toDate` - End date
- `groupBy` - Group by (day, week, month)

**Response (200 OK):**
```json
{
  "totalInterviews": 150,
  "completedInterviews": 135,
  "cancelledInterviews": 10,
  "noShowInterviews": 5,
  "averageDuration": 28.5,
  "averageScore": 75.8,
  "completionRate": 0.9,
  "technicalIssuesRate": 0.03,
  "byStatus": {
    "SCHEDULED": 20,
    "COMPLETED": 135,
    "CANCELLED": 10,
    "NO_SHOW": 5
  },
  "byType": {
    "TECHNICAL": 60,
    "BEHAVIORAL": 45,
    "CULTURAL": 30,
    "MIXED": 15
  },
  "scoreDistribution": {
    "0-20": 5,
    "21-40": 15,
    "41-60": 30,
    "61-80": 55,
    "81-100": 30
  },
  "dailyTrends": [
    {
      "date": "2025-01-09",
      "scheduled": 5,
      "completed": 4,
      "averageScore": 78.5
    }
  ]
}
```

## WebSocket Events

### Interview Session Events
```javascript
// Connect to WebSocket
ws://api.vtbaihr.com/ws/interviews

// Subscribe to interview updates
{
  "action": "subscribe",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000"
}

// Receive updates
{
  "event": "interview.started",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "timestamp": "2025-01-10T10:00:00Z"
}

{
  "event": "ai.question.asked",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "questionId": 1,
  "question": "Tell me about yourself"
}

{
  "event": "candidate.answer.completed",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "questionId": 1,
  "duration": 120
}

{
  "event": "interview.completed",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "totalDuration": 1800
}
```

## Error Responses

### Common Interview Errors
```json
{
  "timestamp": "2025-01-10T10:00:00Z",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "Cannot join interview",
  "path": "/api/v1/interviews/550e8400/join",
  "details": {
    "reason": "TOO_EARLY",
    "minutesUntilStart": 30,
    "scheduledTime": "2025-01-10T10:30:00Z"
  }
}
```

### Daily.co Integration Errors
```json
{
  "timestamp": "2025-01-10T10:00:00Z",
  "status": 503,
  "error": "Service Unavailable",
  "message": "Daily.co service is temporarily unavailable",
  "path": "/api/v1/daily/rooms",
  "details": {
    "provider": "Daily.co",
    "retryAfter": 30
  }
}
```