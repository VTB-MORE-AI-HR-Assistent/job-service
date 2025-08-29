package com.aristurtle.job_service.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class VacancyShortResponse(
    val id: Int,
    val title: String,
    val company: String,
    
    @JsonProperty("salary_from")
    val salaryFrom: Int?,
    
    @JsonProperty("salary_to")
    val salaryTo: Int?,
    
    @JsonProperty("salary_currency")
    val salaryCurrency: String?,

    val location: String?,
    
    @JsonProperty("is_remote")
    val isRemote: Boolean,
    
    val status: String,
    
    @JsonProperty("created_at")
    val createdAt: LocalDateTime
)