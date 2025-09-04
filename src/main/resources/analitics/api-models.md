# API Data Models

## Core DTOs (Data Transfer Objects)

### UserDto
```kotlin
data class UserDto(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: UserRole,
    val isActive: Boolean = true,
    val createdAt: Instant,
    val updatedAt: Instant
)
```

### AuthResponse
```kotlin
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long, // seconds
    val user: UserDto
)
```

### LoginRequest
```kotlin
data class LoginRequest(
    @field:Email
    val email: String,
    
    @field:NotBlank
    @field:Size(min = 6, max = 100)
    val password: String
)
```

### RegisterRequest
```kotlin
data class RegisterRequest(
    @field:Email
    val email: String,
    
    @field:NotBlank
    @field:Size(min = 6, max = 100)
    val password: String,
    
    @field:NotBlank
    @field:Size(min = 2, max = 50)
    val firstName: String,
    
    @field:NotBlank
    @field:Size(min = 2, max = 50)
    val lastName: String,
    
    val role: UserRole = UserRole.HR_MANAGER
)
```

### RefreshTokenRequest
```kotlin
data class RefreshTokenRequest(
    @field:NotBlank
    val refreshToken: String
)
```

### RefreshTokenResponse
```kotlin
data class RefreshTokenResponse(
    val accessToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long // seconds
)
```

## Vacancy Models

### VacancyDto
```kotlin
data class VacancyDto(
    val id: Long?,
    val title: String,
    val department: String,
    val location: String,
    val type: EmploymentType,
    val status: VacancyStatus,
    val description: String,
    val requirements: List<String>,
    val responsibilities: List<String>,
    val skills: List<String>,
    val experience: ExperienceLevel,
    val salary: SalaryRange?,
    val benefits: List<String>,
    val applicantsCount: Int = 0,
    val interviewedCount: Int = 0,
    val hiredCount: Int = 0,
    val createdBy: Long,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val publishedAt: Instant?,
    val closedAt: Instant?
)
```

### CreateVacancyRequest
```kotlin
data class CreateVacancyRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 200)
    val title: String,
    
    @field:NotBlank
    val department: String,
    
    @field:NotBlank
    val location: String,
    
    val type: EmploymentType,
    
    @field:NotBlank
    @field:Size(min = 50, max = 5000)
    val description: String,
    
    @field:NotEmpty
    val requirements: List<String>,
    
    val responsibilities: List<String> = emptyList(),
    val skills: List<String> = emptyList(),
    val experience: ExperienceLevel,
    val salary: SalaryRange?,
    val benefits: List<String> = emptyList()
)
```

### UpdateVacancyRequest
```kotlin
data class UpdateVacancyRequest(
    val title: String?,
    val department: String?,
    val location: String?,
    val type: EmploymentType?,
    val status: VacancyStatus?,
    val description: String?,
    val requirements: List<String>?,
    val responsibilities: List<String>?,
    val skills: List<String>?,
    val experience: ExperienceLevel?,
    val salary: SalaryRange?,
    val benefits: List<String>?
)
```

### SalaryRange
```kotlin
data class SalaryRange(
    val min: BigDecimal,
    val max: BigDecimal,
    val currency: String = "RUB",
    val period: SalaryPeriod = SalaryPeriod.MONTHLY
)
```

## Candidate Models

### CandidateDto
```kotlin
data class CandidateDto(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?,
    val linkedIn: String?,
    val portfolio: String?,
    val currentPosition: String?,
    val currentCompany: String?,
    val experience: Int, // years
    val skills: List<String>,
    val education: List<EducationDto>,
    val workHistory: List<WorkExperienceDto>,
    val languages: List<LanguageDto>,
    val expectedSalary: SalaryRange?,
    val location: String,
    val relocationReady: Boolean,
    val resumeUrl: String?,
    val resumeText: String?,
    val matchScore: Double?,
    val status: CandidateStatus,
    val source: CandidateSource,
    val appliedVacancies: List<Long>,
    val notes: String?,
    val tags: List<String>,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
```

### CreateCandidateRequest
```kotlin
data class CreateCandidateRequest(
    @field:NotBlank
    val firstName: String,
    
    @field:NotBlank
    val lastName: String,
    
    @field:Email
    val email: String,
    
    val phone: String?,
    val linkedIn: String?,
    val portfolio: String?,
    val currentPosition: String?,
    val currentCompany: String?,
    val experience: Int = 0,
    val skills: List<String> = emptyList(),
    val education: List<EducationDto> = emptyList(),
    val workHistory: List<WorkExperienceDto> = emptyList(),
    val languages: List<LanguageDto> = emptyList(),
    val expectedSalary: SalaryRange?,
    val location: String,
    val relocationReady: Boolean = false,
    val vacancyId: Long?,
    val source: CandidateSource = CandidateSource.DIRECT
)
```

### EducationDto
```kotlin
data class EducationDto(
    val institution: String,
    val degree: String,
    val field: String,
    val startYear: Int,
    val endYear: Int?,
    val isOngoing: Boolean = false
)
```

### WorkExperienceDto
```kotlin
data class WorkExperienceDto(
    val company: String,
    val position: String,
    val description: String?,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val isOngoing: Boolean = false,
    val achievements: List<String> = emptyList()
)
```

### LanguageDto
```kotlin
data class LanguageDto(
    val language: String,
    val proficiency: LanguageProficiency
)
```

## Interview Models

### InterviewSessionDto
```kotlin
data class InterviewSessionDto(
    val id: Long?,
    val sessionId: String, // UUID
    val candidateId: Long,
    val vacancyId: Long,
    val candidateName: String,
    val candidateEmail: String,
    val position: String,
    val department: String,
    val scheduledTime: Instant,
    val duration: Int, // minutes
    val type: InterviewType,
    val difficulty: DifficultyLevel,
    val status: InterviewStatus,
    val roomUrl: String?,
    val roomToken: String?,
    val startedAt: Instant?,
    val endedAt: Instant?,
    val actualDuration: Int?, // seconds
    val recordingUrl: String?,
    val transcriptUrl: String?,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
```

### CreateInterviewRequest
```kotlin
data class CreateInterviewRequest(
    @field:NotNull
    val candidateId: Long,
    
    @field:NotNull
    val vacancyId: Long,
    
    @field:Future
    val scheduledTime: Instant,
    
    @field:Min(15)
    @field:Max(180)
    val duration: Int = 30, // minutes
    
    val type: InterviewType = InterviewType.TECHNICAL,
    val difficulty: DifficultyLevel
)
```

### InterviewResultDto
```kotlin
data class InterviewResultDto(
    val id: Long?,
    val interviewId: Long,
    val sessionId: String,
    val overallScore: Double, // 0-100
    val technicalScore: Double?,
    val behavioralScore: Double?,
    val culturalScore: Double?,
    val communicationScore: Double?,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val recommendations: String,
    val aiSummary: String,
    val detailedAnalysis: Map<String, AnalysisSection>,
    val questionsAsked: List<QuestionResultDto>,
    val candidateResponses: List<ResponseDto>,
    val redFlags: List<String>,
    val hiringRecommendation: HiringRecommendation,
    val confidence: Double, // AI confidence 0-1
    val processedAt: Instant,
    val reviewedBy: Long?,
    val reviewedAt: Instant?,
    val reviewNotes: String?
)
```

### QuestionResultDto
```kotlin
data class QuestionResultDto(
    val questionId: Long,
    val question: String,
    val category: QuestionCategory,
    val difficulty: DifficultyLevel,
    val candidateAnswer: String,
    val score: Double, // 0-10
    val aiAnalysis: String,
    val keyPointsCovered: List<String>,
    val keyPointsMissed: List<String>,
    val followUpSuggestions: List<String>
)
```

## Resume Models

### ResumeUploadRequest
```kotlin
data class ResumeUploadRequest(
    val candidateId: Long?,
    val vacancyId: Long?,
    val fileName: String,
    val fileContent: String, // Base64 encoded
    val fileType: String // MIME type
)
```

### ResumeAnalysisResultDto
```kotlin
data class ResumeAnalysisResultDto(
    val id: Long,
    val candidateId: Long,
    val extractedData: ExtractedResumeData,
    val matchScore: Double, // 0-100
    val skillsMatch: List<SkillMatch>,
    val experienceMatch: ExperienceMatch,
    val educationMatch: EducationMatch,
    val overallAssessment: String,
    val strengths: List<String>,
    val gaps: List<String>,
    val recommendations: List<String>,
    val processedAt: Instant
)
```

### ExtractedResumeData
```kotlin
data class ExtractedResumeData(
    val personalInfo: PersonalInfo,
    val summary: String?,
    val skills: List<String>,
    val experience: List<WorkExperienceDto>,
    val education: List<EducationDto>,
    val certifications: List<String>,
    val languages: List<LanguageDto>,
    val projects: List<ProjectDto>
)
```

## Question Bank Models

### QuestionDto
```kotlin
data class QuestionDto(
    val id: Long?,
    val question: String,
    val category: QuestionCategory,
    val subcategory: String?,
    val type: QuestionType,
    val difficulty: DifficultyLevel,
    val skills: List<String>,
    val expectedAnswerPoints: List<String>,
    val followUpQuestions: List<String>,
    val timeLimit: Int?, // seconds
    val isActive: Boolean = true,
    val usageCount: Int = 0,
    val averageScore: Double?,
    val tags: List<String>,
    val createdBy: Long,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
```

### CreateQuestionRequest
```kotlin
data class CreateQuestionRequest(
    @field:NotBlank
    @field:Size(min = 10, max = 1000)
    val question: String,
    
    val category: QuestionCategory,
    val subcategory: String?,
    val type: QuestionType = QuestionType.OPEN_ENDED,
    val difficulty: DifficultyLevel,
    val skills: List<String> = emptyList(),
    val expectedAnswerPoints: List<String> = emptyList(),
    val followUpQuestions: List<String> = emptyList(),
    val timeLimit: Int?,
    val tags: List<String> = emptyList()
)
```

## Analytics Models

### DashboardAnalyticsDto
```kotlin
data class DashboardAnalyticsDto(
    val totalVacancies: Int,
    val activeVacancies: Int,
    val totalCandidates: Int,
    val newCandidatesThisWeek: Int,
    val totalInterviews: Int,
    val scheduledInterviews: Int,
    val completedInterviews: Int,
    val averageMatchScore: Double,
    val averageInterviewScore: Double,
    val hiringRate: Double,
    val timeToHire: Double, // days
    val topSkillsInDemand: List<SkillDemand>,
    val departmentMetrics: List<DepartmentMetric>,
    val weeklyTrends: List<WeeklyTrend>,
    val conversionFunnel: ConversionFunnel
)
```

### VacancyAnalyticsDto
```kotlin
data class VacancyAnalyticsDto(
    val vacancyId: Long,
    val title: String,
    val totalApplicants: Int,
    val qualifiedApplicants: Int,
    val interviewedCandidates: Int,
    val offersExtended: Int,
    val offersAccepted: Int,
    val averageMatchScore: Double,
    val averageInterviewScore: Double,
    val timeToFill: Int?, // days
    val sourceBreakdown: Map<CandidateSource, Int>,
    val skillsAnalysis: List<SkillMatch>,
    val rejectionReasons: Map<String, Int>,
    val candidatePipeline: List<PipelineStage>
)
```

## Enums

### UserRole
```kotlin
enum class UserRole {
    ADMIN,
    HR_MANAGER,
    HR_SPECIALIST,
    RECRUITER,
    HIRING_MANAGER,
    CANDIDATE
}
```

### VacancyStatus
```kotlin
enum class VacancyStatus {
    DRAFT,
    ACTIVE,
    PAUSED,
    CLOSED,
    ARCHIVED
}
```

### EmploymentType
```kotlin
enum class EmploymentType {
    FULL_TIME,
    PART_TIME,
    CONTRACT,
    INTERNSHIP,
    FREELANCE
}
```

### ExperienceLevel
```kotlin
enum class ExperienceLevel {
    ENTRY,
    JUNIOR,
    MIDDLE,
    SENIOR,
    LEAD,
    EXECUTIVE
}
```

### CandidateStatus
```kotlin
enum class CandidateStatus {
    NEW,
    SCREENING,
    SHORTLISTED,
    INTERVIEW_SCHEDULED,
    INTERVIEW_COMPLETED,
    TECHNICAL_TEST,
    REFERENCE_CHECK,
    OFFER_EXTENDED,
    OFFER_ACCEPTED,
    OFFER_DECLINED,
    HIRED,
    REJECTED,
    WITHDRAWN
}
```

### CandidateSource
```kotlin
enum class CandidateSource {
    DIRECT,
    REFERRAL,
    LINKEDIN,
    HEADHUNTER,
    JOB_BOARD,
    UNIVERSITY,
    INTERNAL,
    OTHER
}
```

### InterviewStatus
```kotlin
enum class InterviewStatus {
    SCHEDULED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
    NO_SHOW,
    RESCHEDULED,
    TECHNICAL_ISSUE
}
```

### InterviewType
```kotlin
enum class InterviewType {
    SCREENING,
    TECHNICAL,
    BEHAVIORAL,
    CULTURAL,
    FINAL,
    MIXED
}
```

### DifficultyLevel
```kotlin
enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD,
    EXPERT
}
```

### QuestionCategory
```kotlin
enum class QuestionCategory {
    TECHNICAL,
    BEHAVIORAL,
    SITUATIONAL,
    CULTURAL,
    GENERAL,
    ROLE_SPECIFIC
}
```

### QuestionType
```kotlin
enum class QuestionType {
    OPEN_ENDED,
    MULTIPLE_CHOICE,
    CODING,
    CASE_STUDY,
    SCENARIO
}
```

### LanguageProficiency
```kotlin
enum class LanguageProficiency {
    NATIVE,
    FLUENT,
    ADVANCED,
    INTERMEDIATE,
    BASIC
}
```

### HiringRecommendation
```kotlin
enum class HiringRecommendation {
    STRONGLY_RECOMMEND,
    RECOMMEND,
    NEUTRAL,
    NOT_RECOMMEND,
    STRONGLY_NOT_RECOMMEND
}
```

### SalaryPeriod
```kotlin
enum class SalaryPeriod {
    HOURLY,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}
```