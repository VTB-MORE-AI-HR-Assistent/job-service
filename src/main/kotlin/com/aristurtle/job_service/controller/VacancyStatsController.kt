package com.aristurtle.job_service.controller

import com.aristurtle.job_service.service.VacancyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vacancies")
@Tag(name = "Vacancy Statistics", description = "API для получения статистики по вакансиям")
class VacancyStatsController(
    private val vacancyService: VacancyService
) {
    @Operation(
        summary = "Получить статистику вакансий",
        description = "Возвращает статистику по всем вакансиям"
    )
    @ApiResponse(responseCode = "200", description = "Успешное получение статистики")
    @GetMapping("/stats")
    fun getVacancyStats(): ResponseEntity<Map<String, Any>> {
        val stats = vacancyService.getVacancyStats()
        return ResponseEntity.ok(stats)
    }
}
