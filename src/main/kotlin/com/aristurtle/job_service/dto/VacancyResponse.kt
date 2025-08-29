package com.aristurtle.job_service.dto

import io.swagger.v3.oas.annotations.media.Schema
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

@Schema(description = "Ответ с данными вакансии")
data class VacancyResponse(
    @Schema(description = "ID вакансии", example = "1")
    val id: Int,

    @Schema(description = "Название вакансии", example = "Java-разработчик")
    val title: String,

    @Schema(description = "Название компании", example = "TechCorp")
    val company: String,

    @Schema(description = "Описание вакансии", example = "Разработка backend на Java")
    val description: String?,

    @Schema(description = "Требования к кандидату", example = "Опыт работы с Spring Boot")
    val requirements: String?,

    @Schema(description = "Контакты для отклика", example = "hr@techcorp.com")
    val contacts: String?,

    @Schema(description = "Зарплата 'от'", example = "150000")
    @JsonProperty("salary_from")
    val salaryFrom: Int?,

    @Schema(description = "Зарплата 'до'", example = "250000")
    @JsonProperty("salary_to")
    val salaryTo: Int?,

    @Schema(description = "Валюта зарплаты", example = "RUB")
    @JsonProperty("salary_currency")
    val salaryCurrency: String?,

    @Schema(description = "Флаг 'до вычета налогов'", example = "true")
    @JsonProperty("salary_gross")
    val salaryGross: Boolean?,

    @Schema(description = "Опыт работы", example = "от 3 лет")
    val experience: String?,

    @Schema(description = "Тип занятости", example = "полная")
    @JsonProperty("employment_type")
    val employmentType: String?,

    @Schema(description = "График работы", example = "удаленная работа")
    @JsonProperty("work_schedule")
    val workSchedule: String?,

    @Schema(description = "Местоположение", example = "Москва")
    val location: String?,

    @Schema(description = "Флаг удаленной работы", example = "true")
    @JsonProperty("is_remote")
    val isRemote: Boolean,

    @Schema(description = "Статус вакансии", example = "active")
    val status: String,

    @Schema(description = "Дата создания", example = "2024-01-15T10:30:00")
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @Schema(description = "Дата обновления", example = "2024-01-15T10:30:00")
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime
)