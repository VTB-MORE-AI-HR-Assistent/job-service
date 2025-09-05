package com.aristurtle.job_service.controller

import com.aristurtle.job_service.dto.VacancyRequest
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
@RequestMapping("/api/vacancies")
@Tag(name = "Vacancies", description = "API для управления вакансиями")
class VacancyController(
    private val vacancyService: VacancyService
) {
    companion object {
        private val modelMapper = ModelMapper()
    }

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
    @GetMapping("/{id}")
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
            content = [Content(schema = Schema(implementation = VacancyRequest::class))]
        )
        @RequestBody vacancyRequest: VacancyRequest
    ): ResponseEntity<Vacancy> {
        val createdVacancy = vacancyService.createVacancy(
            modelMapper.map<Vacancy>(vacancyRequest, Vacancy::class.java)
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
    @PutMapping("/{id}")
    fun updateVacancy(
        @Parameter(description = "ID вакансии", required = true, example = "1")
        @PathVariable id: Long,

        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Обновленные данные вакансии",
            required = true,
            content = [Content(schema = Schema(implementation = VacancyRequest::class))]
        )
        @RequestBody vacancyRequest: VacancyRequest
    ): ResponseEntity<Vacancy> {
        return try {
            val updatedVacancy = vacancyService.updateVacancy(
                id = id,
                vacancy = modelMapper.map<Vacancy>(vacancyRequest, Vacancy::class.java)
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
    @DeleteMapping("/{id}")
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
}