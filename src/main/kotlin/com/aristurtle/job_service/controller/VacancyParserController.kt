package com.aristurtle.job_service.controller

import com.aristurtle.job_service.dto.UploadRequest
import com.aristurtle.job_service.dto.VacancyDto
import com.aristurtle.job_service.service.VacancyService
import com.aristurtle.job_service.service.impl.VacancyParserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.tags.Tag
import org.modelmapper.ModelMapper
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/vacancies")
@Tag(name = "Vacancy Parser", description = "API для управления парсером вакансий")
class VacancyParserController(
    private val vacancyParserService: VacancyParserService,
    private val vacancyService: VacancyService,
    private val modelMapper: ModelMapper
) {

    @Operation(
        summary = "Создать задачу на загрузку",
        description = "Отправьте один или несколько файлов резюме в формате multipart/form-data.\n\n" +
                "Части запроса:\n" +
                "- file: файл формата multipart/form-data (обязательно)\n" +
                "- jobId: целое число (необязательно)\n\n" +
                "Поддерживаемые типы: application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document (DOCX), text/plain",
        requestBody = RequestBody(
            required = true,
            content = [
                Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = Schema(implementation = UploadRequest::class)
                )
            ]
        )
    )
    @PostMapping("/upload")
    fun parseVacancyFromFile(@ModelAttribute request: UploadRequest): ResponseEntity<Any> {
        if (request.file.isEmpty)
            return ResponseEntity.badRequest().body("File must not be empty")
        val vacancy = vacancyParserService.parseVacancyFromFile(request.file)
        val savedVacancy = vacancyService.createVacancy(vacancy)
        return ResponseEntity.ok(savedVacancy)
    }

    @Operation(
        summary = "Парсинг файла вакансии",
        description = "Отправьте файл вакансии в формате multipart/form-data.\n\n" +
                "Части запроса:\n" +
                "- files: массив бинарных файлов (обязательно)\n" +
                "- jobId: целое число (необязательно)\n\n" +
                "Поддерживаемые типы: application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document (DOCX), text/plain",
        requestBody = RequestBody(
            required = true,
            content = [
                Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = Schema(implementation = UploadRequest::class)
                )
            ]
        )
    )
    @PostMapping("/parse-file")
    fun previewParsedVacancy(@ModelAttribute request: UploadRequest): ResponseEntity<Any> {
        if (request.file.isEmpty)
            return ResponseEntity.badRequest().body("Files must not be empty")
        val vacancy = vacancyParserService.parseVacancyFromFile(request.file)
        return ResponseEntity.ok(modelMapper.map(vacancy, VacancyDto::class.java))
    }
}