package com.aristurtle.job_service.dto

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile

@Schema(name = "UploadRequest", description = "Multipart form-data payload for creating an upload job")
data class UploadRequest(
    @field:Positive(message = "jobId must be positive")
    @Schema(description = "Optional job id to associate candidates with", example = "123", nullable = true)
    val jobId: Long? = null,

    @field:NotEmpty(message = "files must not be empty")
    @field:Size(max = 20, message = "files size must be <= 20")
    @field:ArraySchema(
        schema = Schema(type = "string", format = "binary"),
        arraySchema = Schema(
            description = "One vacancy files to parse (pdf, doc, docx, rtf, txt)",
            required = true
        )
    )
    val file: MultipartFile
)