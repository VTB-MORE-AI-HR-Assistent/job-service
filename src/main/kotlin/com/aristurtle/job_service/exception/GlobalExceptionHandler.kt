package com.aristurtle.job_service.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(UnsupportedFileTypeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUnsupportedFileTypeException(ex: UnsupportedFileTypeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(
                error = "Unsupported File Type",
                message = ex.message ?: "The provided file type is not supported",
                status = HttpStatus.BAD_REQUEST.value()
            ))
    }

    @ExceptionHandler(FileParsingFailException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleFileParsingFailException(ex: FileParsingFailException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(
                error = "File Parsing Failed",
                message = ex.message ?: "Failed to parse the uploaded file",
                status = HttpStatus.BAD_REQUEST.value()
            ))
    }

    @ExceptionHandler(AiParsingFailException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAiParsingFailException(ex: AiParsingFailException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(
                error = "AI Parsing Failed",
                message = ex.message ?: "Failed to parse content with AI",
                status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            ))
    }
}

data class ErrorResponse(
    val error: String,
    val message: String,
    val status: Int
)