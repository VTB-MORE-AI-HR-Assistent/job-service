package com.aristurtle.job_service.controller

import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.dto.VacancyResponse
import com.aristurtle.job_service.dto.VacancyShortResponse
import com.aristurtle.job_service.mapper.VacancyMapper
import com.aristurtle.job_service.service.VacancyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vacancies")
@Tag(name = "Vacancies", description = "API для управления вакансиями")
class VacancyController(
    private val vacancyService: VacancyService
) {

    @GetMapping
    @Operation(
        summary = "Получить все вакансии",
        description = "Возвращает список всех вакансий в кратком формате"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Список вакансий получен успешно",
        content = [Content(mediaType = "application/json")]
    )
    fun getAllVacancies(): List<VacancyShortResponse> {
        return vacancyService.getAllVacancies().map { VacancyMapper.toShortResponse(it) }
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Получить вакансию по ID",
        description = "Возвращает полную информацию о вакансии по её идентификатору"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Вакансия найдена",
            content = [Content(mediaType = "application/json")]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Вакансия не найдена",
            content = [Content(mediaType = "application/json")]
        )
    ])
    fun getVacancyById(@PathVariable id: Int): VacancyResponse {
        return VacancyMapper.toResponse(vacancyService.getVacancyById(id))
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Создать новую вакансию",
        description = "Создает новую вакансию с указанными данными"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Вакансия успешно создана",
            content = [Content(mediaType = "application/json")]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Неверные данные запроса",
            content = [Content(mediaType = "application/json")]
        )
    ])
    fun createVacancy(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные для создания вакансии",
            required = true,
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(
                    name = "Пример запроса",
                    value = """
                    {
                      "title": "Java-разработчик",
                      "company": "TechCorp",
                      "description": "Разработка backend на Java",
                      "salary_from": 150000,
                      "salary_to": 250000,
                      "salary_currency": "RUB",
                      "is_remote": true
                    }
                    """
                )]
            )]
        )
        @Valid @RequestBody request: VacancyRequest
    ): VacancyResponse {
        val vacancy = VacancyMapper.toEntity(request)
        val savedVacancy = vacancyService.createVacancy(vacancy)
        return VacancyMapper.toResponse(savedVacancy)
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Обновить вакансию",
        description = "Обновляет данные существующей вакансии"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Вакансия успешно обновлена",
            content = [Content(mediaType = "application/json")]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Вакансия не найдена",
            content = [Content(mediaType = "application/json")]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Неверные данные запроса",
            content = [Content(mediaType = "application/json")]
        )
    ])
    fun updateVacancy(
        @Parameter(description = "ID вакансии для обновления", example = "1", required = true)
        @PathVariable id: Int,

        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Новые данные вакансии",
            required = true
        )
        @Valid @RequestBody request: VacancyRequest
    ): VacancyResponse {
        val updatedVacancy = vacancyService.updateVacancy(id, request)
        return VacancyMapper.toResponse(updatedVacancy)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Удалить вакансию",
        description = "Удаляет вакансию по её идентификатору"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "204",
            description = "Вакансия успешно удалена"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Вакансия не найдена",
            content = [Content(mediaType = "application/json")]
        )
    ])
    fun deleteVacancy(
        @Parameter(description = "ID вакансии для удаления", example = "1", required = true)
        @PathVariable id: Int
    ) {
        vacancyService.deleteVacancy(id)
    }

    @GetMapping("/search")
    @Operation(
        summary = "Поиск вакансий",
        description = "Поиск вакансий по различным критериям фильтрации"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Результаты поиска",
        content = [Content(mediaType = "application/json")]
    )
    fun searchVacancies(
        @Parameter(description = "Название вакансии (поиск по подстроке)", example = "Java")
        @RequestParam(required = false) title: String?,

        @Parameter(description = "Название компании (поиск по подстроке)", example = "Tech")
        @RequestParam(required = false) company: String?,

        @Parameter(description = "Местоположение (поиск по подстроке)", example = "Москва")
        @RequestParam(required = false) location: String?,

        @Parameter(description = "Удаленная работа", example = "true")
        @RequestParam(required = false) isRemote: Boolean?,
    ): List<VacancyShortResponse> {
        val vacancies = vacancyService.searchVacancies(title, company, location, isRemote)
        return vacancies.map { VacancyMapper.toShortResponse(it) }
    }
}