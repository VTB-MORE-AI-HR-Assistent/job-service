package com.aristurtle.job_service.controller

import com.aristurtle.job_service.dto.VacancyDto
import com.aristurtle.job_service.dto.VacancySearchCriteria
import com.aristurtle.job_service.model.Vacancy
import com.aristurtle.job_service.service.VacancyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.modelmapper.ModelMapper
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vacancies")
@Tag(name = "Vacancies", description = "API для управления вакансиями")
class VacancyController(
    private val vacancyService: VacancyService,
    private val modelMapper: ModelMapper
) {
    @Operation(
        summary = "Получить все вакансии",
        description = "Возвращает список вакансий с возможностью пагинации"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Успешное получение списка вакансий"),
            ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
        ]
    )
    @GetMapping
    fun getAllVacancies(
        @Parameter(name = "pageNumber", description = "Параметр пагинации: номер начальной страницы", required = false)
        @RequestParam("pageNumber", required = false) pageNumber: Int?,

        @Parameter(name = "pageSize", description = "Параметр пагинации: объем возвращаемых страниц", required = false)
        @RequestParam("pageSize", required = false) pageSize: Int?,
    ): ResponseEntity<List<Vacancy>> {
        return if (pageNumber != null && pageSize != null)
            vacancyService.getAll(PageRequest.of(pageNumber!!, pageSize!!))
                .toList()
                .let { ResponseEntity.ok(it) }
        else
            vacancyService.getAll().let { ResponseEntity.ok(it) }
    }

    @Operation(
        summary = "Получить вакансию по ID",
        description = "Возвращает вакансию по указанному идентификатору"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Вакансия найдена"),
            ApiResponse(responseCode = "404", description = "Вакансия не найдена")
        ]
    )
    @GetMapping("/{id:[0-9]+}")
    fun getVacancyById(
        @Parameter(description = "ID вакансии", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Vacancy> {
        return vacancyService.getVacancyById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @Operation(
        summary = "Создать новую вакансию",
        description = "Создает новую вакансию и возвращает созданный объект"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Вакансия успешно создана",
                content = [Content(schema = Schema(implementation = Vacancy::class))]
            ),
            ApiResponse(responseCode = "400", description = "Неверные данные вакансии")
        ]
    )
    @PostMapping
    fun createVacancy(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные вакансии",
            required = true,
            content = [Content(schema = Schema(implementation = VacancyDto::class))]
        )
        @RequestBody vacancyDto: VacancyDto
    ): ResponseEntity<Vacancy> {
        val createdVacancy = vacancyService.createVacancy(
            modelMapper.map(vacancyDto, Vacancy::class.java)
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVacancy)
    }

    @Operation(
        summary = "Обновить вакансию",
        description = "Обновляет данные вакансии по указанному ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Вакансия успешно обновлена"),
            ApiResponse(responseCode = "404", description = "Вакансия не найдена")
        ]
    )
    @PutMapping("/{id:[0-9]+}")
    fun updateVacancy(
        @Parameter(description = "ID вакансии", required = true, example = "1")
        @PathVariable id: Long,

        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Обновленные данные вакансии",
            required = true,
            content = [Content(schema = Schema(implementation = VacancyDto::class))]
        )
        @RequestBody vacancyDto: VacancyDto
    ): ResponseEntity<Vacancy> {
        return try {
            val updatedVacancy = vacancyService.updateVacancy(
                id = id,
                vacancy = modelMapper.map(vacancyDto, Vacancy::class.java)
            )
            ResponseEntity.ok(updatedVacancy)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(
        summary = "Удалить вакансию",
        description = "Удаляет вакансию по указанному ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Вакансия успешно удалена"),
            ApiResponse(responseCode = "404", description = "Вакансия не найдена")
        ]
    )
    @DeleteMapping("/{id:[0-9]+}")
    fun deleteVacancy(
        @Parameter(description = "ID вакансии", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        return if (vacancyService.deleteVacancy(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(
        summary = "Поиск вакансий",
        description = "Расширенный поиск вакансий по различным критериям. Критерии описываются в формате JSON по схеме VacancySearchCriteria"
    )
    @ApiResponse(responseCode = "200", description = "Успешный поиск")
    @PostMapping("/search")
    fun searchVacancies(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Поисковый объект",
            required = true,
            content = [Content(schema = Schema(implementation = VacancySearchCriteria::class))]
        )
        @RequestBody vacancySearchCriteria: VacancySearchCriteria,

        @Parameter(name = "pageNumber", description = "Параметр пагинации: номер начальной страницы", required = false)
        @RequestParam("pageNumber", required = false) pageNumber: Int?,

        @Parameter(name = "pageSize", description = "Параметр пагинации: объем возвращаемых страниц", required = false)
        @RequestParam("pageSize", required = false) pageSize: Int?,
    ): ResponseEntity<List<Vacancy>> {
        val vacancies =
            if (pageNumber != null && pageSize != null)
                vacancyService.searchVacancies(vacancySearchCriteria, PageRequest.of(pageNumber, pageSize))
            else
                vacancyService.searchVacancies(vacancySearchCriteria)
        return ResponseEntity.ok(vacancies)
    }

    @Operation(
        summary = "Получить кандидатов по вакансии",
        description = "Возвращает список кандидатов для указанной вакансии"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Список кандидатов получен"),
            ApiResponse(responseCode = "404", description = "Вакансия не найдена")
        ]
    )
    @GetMapping("/{id:[0-9]+}/candidates")
    fun getVacancyCandidates(
        @Parameter(description = "ID вакансии", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<List<Map<String, Any>>> {
        return if (vacancyService.getVacancyById(id).isPresent) {
            // TODO: Интегрировать с candidate-service для получения реальных кандидатов
            val mockCandidates = listOf(
                mapOf(
                    "id" to 1,
                    "firstName" to "Иван",
                    "lastName" to "Иванов", 
                    "email" to "ivan@example.com",
                    "phone" to "+7900123456",
                    "status" to "ACTIVE",
                    "appliedAt" to "2025-01-01T10:00:00Z"
                ),
                mapOf(
                    "id" to 2,
                    "firstName" to "Петр",
                    "lastName" to "Петров",
                    "email" to "petr@example.com", 
                    "phone" to "+7900654321",
                    "status" to "INTERVIEWING",
                    "appliedAt" to "2025-01-02T14:30:00Z"
                )
            )
            ResponseEntity.ok(mockCandidates)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(
        summary = "Архивировать вакансию",
        description = "Переводит вакансию в архивный статус"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Вакансия заархивирована"),
            ApiResponse(responseCode = "404", description = "Вакансия не найдена")
        ]
    )
    @PostMapping("/{id:[0-9]+}/archive")
    fun archiveVacancy(
        @Parameter(description = "ID вакансии", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Vacancy> {
        return try {
            val archivedVacancy = vacancyService.updateVacancyStatus(id, "ARCHIVED")
            ResponseEntity.ok(archivedVacancy)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(
        summary = "Опубликовать вакансию",
        description = "Переводит вакансию в опубликованный статус"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Вакансия опубликована"),
            ApiResponse(responseCode = "404", description = "Вакансия не найдена")
        ]
    )
    @PostMapping("/{id:[0-9]+}/publish")
    fun publishVacancy(
        @Parameter(description = "ID вакансии", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Vacancy> {
        return try {
            val publishedVacancy = vacancyService.updateVacancyStatus(id, "PUBLISHED")
            ResponseEntity.ok(publishedVacancy)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(
        summary = "Приостановить вакансию",
        description = "Переводит вакансию в приостановленный статус"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Вакансия приостановлена"),
            ApiResponse(responseCode = "404", description = "Вакансия не найдена")
        ]
    )
    @PostMapping("/{id:[0-9]+}/pause")
    fun pauseVacancy(
        @Parameter(description = "ID вакансии", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Vacancy> {
        return try {
            val pausedVacancy = vacancyService.updateVacancyStatus(id, "PAUSED")
            ResponseEntity.ok(pausedVacancy)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}