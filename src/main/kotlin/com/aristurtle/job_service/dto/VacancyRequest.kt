package com.aristurtle.job_service.dto

import io.swagger.v3.oas.annotations.media.Schema
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

@Schema(description = "Запрос на создание или обновление вакансии")
data class VacancyRequest(
    @field:Schema(
        description = "Название вакансии",
        example = "Java-разработчик",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "Title is required")
    @field:Size(max = 255, message = "Title must be less than 255 characters")
    val title: String,

    @field:Schema(
        description = "Название компании",
        example = "TechCorp",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @field:NotBlank(message = "Company is required")
    @field:Size(max = 255, message = "Company must be less than 255 characters")
    val company: String,

    @field:Schema(
        description = "Описание вакансии",
        example = "Мы ищем опытного Java-разработчика для работы над высоконагруженным проектом"
    )
    val description: String? = null,

    @field:Schema(
        description = "Требования к кандидату",
        example = "Опыт работы с Spring Boot от 3 лет, знание SQL, опыт работы в команде"
    )
    val requirements: String? = null,

    @field:Schema(
        description = "Контакты для отклика",
        example = "hr@techcorp.com, +7 (999) 123-45-67"
    )
    val contacts: String? = null,

    @field:Schema(
        description = "Зарплата 'от'",
        example = "150000",
        minimum = "0"
    )
    @field:PositiveOrZero(message = "Salary from must be positive or zero")
    @JsonProperty("salary_from")
    val salaryFrom: Int? = null,

    @field:Schema(
        description = "Зарплата 'до'",
        example = "250000",
        minimum = "0"
    )
    @field:PositiveOrZero(message = "Salary to must be positive or zero")
    @JsonProperty("salary_to")
    val salaryTo: Int? = null,

    @field:Schema(
        description = "Валюта зарплаты",
        example = "RUB",
        allowableValues = ["RUB", "USD", "EUR", "KZT", "UAH", "BYN"]
    )
    @field:Size(max = 10, message = "Currency code must be less than 10 characters")
    @JsonProperty("salary_currency")
    val salaryCurrency: String? = null,

    @field:Schema(
        description = "Флаг 'до вычета налогов' (gross)",
        example = "true"
    )
    @JsonProperty("salary_gross")
    val salaryGross: Boolean? = null,

    @field:Schema(
        description = "Опыт работы",
        example = "от 3 лет",
        maxLength = 50
    )
    @field:Size(max = 50, message = "Experience must be less than 50 characters")
    val experience: String? = null,

    @field:Schema(
        description = "Тип занятости",
        example = "полная",
        maxLength = 50
    )
    @field:Size(max = 50, message = "Employment type must be less than 50 characters")
    @JsonProperty("employment_type")
    val employmentType: String? = null,

    @field:Schema(
        description = "График работы",
        example = "удаленная работа",
        maxLength = 50
    )
    @field:Size(max = 50, message = "Work schedule must be less than 50 characters")
    @JsonProperty("work_schedule")
    val workSchedule: String? = null,

    @field:Schema(
        description = "Местоположение",
        example = "Москва",
        maxLength = 255
    )
    @field:Size(max = 255, message = "Location must be less than 255 characters")
    val location: String? = null,

    @field:Schema(
        description = "Флаг удаленной работы",
        example = "true"
    )
    @JsonProperty("is_remote")
    val isRemote: Boolean = false,

    @field:Schema(
        description = "Статус вакансии",
        example = "active",
        allowableValues = ["active", "on_pause", "closed"],
        defaultValue = "active"
    )

    @field:Size(max = 50, message = "Status must be less than 50 characters")
    val status: String? = "active"
)