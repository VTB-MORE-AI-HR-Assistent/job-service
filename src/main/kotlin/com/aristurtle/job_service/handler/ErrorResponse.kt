package com.aristurtle.job_service.handler

import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val message: String,
    val details: String?,
    val timestamp: LocalDateTime
)