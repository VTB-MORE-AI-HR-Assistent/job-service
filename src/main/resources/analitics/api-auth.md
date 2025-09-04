# Authentication API Documentation

## Overview
JWT-based authentication with access and refresh tokens. All protected endpoints require Bearer token in Authorization header.

## Endpoints

### 1. Register New User
```http
POST /api/v1/auth/register
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response (201 Created):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "HR_MANAGER",
    "isActive": true,
    "createdAt": "2025-01-09T10:30:00Z",
    "updatedAt": "2025-01-09T10:30:00Z"
  }
}
```

**Error Responses:**
- `400 Bad Request` - Invalid input data
- `409 Conflict` - Email already registered

### 2. Login
```http
POST /api/v1/auth/login
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "SecurePassword123!"
}
```

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "HR_MANAGER",
    "isActive": true,
    "createdAt": "2025-01-09T10:30:00Z",
    "updatedAt": "2025-01-09T10:30:00Z"
  }
}
```

**Error Responses:**
- `400 Bad Request` - Invalid input
- `401 Unauthorized` - Invalid credentials
- `403 Forbidden` - Account disabled

### 3. Refresh Access Token
```http
POST /api/v1/auth/refresh
```

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

**Error Responses:**
- `401 Unauthorized` - Invalid or expired refresh token
- `403 Forbidden` - Refresh token revoked

### 4. Get Current User
```http
GET /api/v1/users/me
```

**Headers:**
```
Authorization: Bearer <access_token>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "HR_MANAGER",
  "isActive": true,
  "createdAt": "2025-01-09T10:30:00Z",
  "updatedAt": "2025-01-09T10:30:00Z"
}
```

**Error Responses:**
- `401 Unauthorized` - Missing or invalid token
- `403 Forbidden` - Account disabled

### 5. Update User Profile
```http
PUT /api/v1/users/me
```

**Headers:**
```
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Smith",
  "phone": "+7 999 123-45-67",
  "department": "HR",
  "position": "Senior HR Manager"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Smith",
  "phone": "+7 999 123-45-67",
  "department": "HR",
  "position": "Senior HR Manager",
  "role": "HR_MANAGER",
  "isActive": true,
  "createdAt": "2025-01-09T10:30:00Z",
  "updatedAt": "2025-01-09T11:30:00Z"
}
```

**Error Responses:**
- `400 Bad Request` - Invalid input
- `401 Unauthorized` - Missing or invalid token

### 6. Change Password
```http
POST /api/v1/users/me/change-password
```

**Headers:**
```
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "currentPassword": "OldPassword123!",
  "newPassword": "NewSecurePassword456!"
}
```

**Response (204 No Content)**

**Error Responses:**
- `400 Bad Request` - Password doesn't meet requirements
- `401 Unauthorized` - Current password incorrect
- `403 Forbidden` - Account locked

### 7. Request Password Reset
```http
POST /api/v1/auth/reset-password-request
```

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

**Response (200 OK):**
```json
{
  "message": "Password reset link sent to email"
}
```

**Note:** Always returns 200 OK to prevent email enumeration

### 8. Reset Password with Token
```http
POST /api/v1/auth/reset-password
```

**Request Body:**
```json
{
  "token": "reset_token_from_email",
  "newPassword": "NewSecurePassword789!"
}
```

**Response (204 No Content)**

**Error Responses:**
- `400 Bad Request` - Invalid or expired token
- `422 Unprocessable Entity` - Password doesn't meet requirements

### 9. Logout (Invalidate Refresh Token)
```http
POST /api/v1/auth/logout
```

**Headers:**
```
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (204 No Content)**

### 10. Validate Token
```http
GET /api/v1/auth/validate
```

**Headers:**
```
Authorization: Bearer <access_token>
```

**Response (200 OK):**
```json
{
  "valid": true,
  "expiresAt": "2025-01-09T11:30:00Z",
  "userId": 1,
  "email": "user@example.com",
  "role": "HR_MANAGER"
}
```

**Error Responses:**
- `401 Unauthorized` - Invalid or expired token

## JWT Token Structure

### Access Token Claims
```json
{
  "sub": "1",              // User ID
  "email": "user@example.com",
  "role": "HR_MANAGER",
  "type": "access",
  "iat": 1704796800,      // Issued at
  "exp": 1704800400,      // Expires (1 hour)
  "jti": "unique_token_id"
}
```

### Refresh Token Claims
```json
{
  "sub": "1",              // User ID
  "type": "refresh",
  "iat": 1704796800,      // Issued at
  "exp": 1705401600,      // Expires (7 days)
  "jti": "unique_token_id"
}
```

## Security Requirements

### Password Policy
- Minimum 8 characters
- At least 1 uppercase letter
- At least 1 lowercase letter
- At least 1 number
- At least 1 special character
- Cannot contain user's email or name
- Cannot be a common password

### Token Security
- Access tokens expire in 1 hour
- Refresh tokens expire in 7 days
- Tokens are signed with RS256 or HS256
- Refresh tokens are stored in database (can be revoked)
- Old refresh tokens are invalidated on use

### Rate Limiting
- Login: 5 attempts per 15 minutes per IP
- Register: 3 attempts per hour per IP
- Password reset: 3 attempts per hour per email

### Account Security
- Email verification required for new accounts
- Account lockout after 5 failed login attempts
- 2FA support (optional, TOTP-based)
- Session management (view/terminate active sessions)

## OAuth2 Integration (Future)

### Supported Providers
- Google OAuth2
- Microsoft Azure AD
- GitHub OAuth

### OAuth2 Flow
```http
GET /api/v1/auth/oauth/{provider}
Redirects to provider authorization page

GET /api/v1/auth/oauth/{provider}/callback?code={auth_code}
Exchanges code for tokens and creates/links user account
```

## Error Response Format
```json
{
  "timestamp": "2025-01-09T10:30:00Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid credentials",
  "path": "/api/v1/auth/login",
  "details": {
    "attemptsRemaining": 3,
    "lockoutTime": null
  }
}
```

## Implementation Notes

### Kotlin Spring Security Configuration
```kotlin
@Configuration
@EnableWebSecurity
class SecurityConfig {
    
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .sessionManagement { 
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
            }
            .authorizeHttpRequests {
                it.requestMatchers("/api/v1/auth/**").permitAll()
                it.requestMatchers("/api/v1/health").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter(), 
                UsernamePasswordAuthenticationFilter::class.java)
            
        return http.build()
    }
}
```

### JWT Service Interface
```kotlin
interface JwtService {
    fun generateAccessToken(user: User): String
    fun generateRefreshToken(user: User): String
    fun validateToken(token: String): Boolean
    fun extractUserId(token: String): Long
    fun extractClaims(token: String): Claims
}
```

### User Service Interface
```kotlin
interface UserService {
    suspend fun register(request: RegisterRequest): AuthResponse
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun refreshToken(refreshToken: String): RefreshTokenResponse
    suspend fun getCurrentUser(userId: Long): UserDto
    suspend fun updateProfile(userId: Long, request: UpdateProfileRequest): UserDto
    suspend fun changePassword(userId: Long, request: ChangePasswordRequest)
    suspend fun requestPasswordReset(email: String)
    suspend fun resetPassword(token: String, newPassword: String)
}
```