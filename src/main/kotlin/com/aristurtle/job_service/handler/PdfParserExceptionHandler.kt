package com.aristurtle.job_service.handler

import com.aristurtle.job_service.exception.NoLlmAnswerFound
import com.aristurtle.job_service.exception.PdfParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException
import java.time.LocalDateTime

@RestControllerAdvice
class PdfParserExceptionHandler {

    @ExceptionHandler(PdfParseException::class)
    fun handlePdfParseException(ex: PdfParseException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "PDF parse error",
            details = ex.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.badRequest().body(error)
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxSizeException(ex: MaxUploadSizeExceededException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Размер файла превышает допустимый лимит",
            details = "Максимальный размер файла: 10MB",
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.badRequest().body(error)
    }

    @ExceptionHandler(NoLlmAnswerFound::class)
    fun handleAiApiException(ex: NoLlmAnswerFound): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            status = HttpStatus.SERVICE_UNAVAILABLE.value(),
            message = "Ошибка сервиса AI",
            details = ex.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error)
    }
}