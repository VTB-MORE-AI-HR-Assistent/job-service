package com.aristurtle.job_service.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class VacancyRequest(
    val status: String? = null,
    val region: String? = null,
    val city: String? = null,
    val address: String? = null,
    @JsonProperty(value = "workType")
    val workType: String? = null,
    @JsonProperty(value = "employmentType")
    val employmentType: String? = null,
    val income: Int? = null,
    @JsonProperty(value = "salaryMax")
    val salaryMax: Int? = null,
    @JsonProperty(value = "salaryMin")
    val salaryMin: Int? = null,
    @JsonProperty(value = "positionTitle")
    val positionTitle: String? = null,
    @JsonProperty(value = "workSchedule")
    val workSchedule: String? = null,
    @JsonProperty(value = "annualBonus")
    val annualBonus: Int? = null,
    val bonusType: String? = null,
    val responsibilities: List<String> = emptyList(),
    val requirements: List<String> = emptyList(),
    @JsonProperty(value = "educationType")
    val educationType: String? = null,
    @JsonProperty(value = "experienceFrom")
    val experienceFrom: Int? = null,
    @JsonProperty(value = "experienceTo")
    val experienceTo: Int? = null,
    @JsonProperty(value = "knowledgeLanguages")
    val knowledgeLanguages: List<String> = emptyList(),
    @JsonProperty(value = "levelLanguages")
    val levelLanguages: List<String> = emptyList(),
    @JsonProperty(value = "businessTrips")
    val businessTrips: Boolean? = null,
    @JsonProperty(value = "additionalInfo")
    val additionalInfo: String? = null,
    @JsonProperty(value = "programRequirements")
    val programRequirements: List<String> = emptyList()
)