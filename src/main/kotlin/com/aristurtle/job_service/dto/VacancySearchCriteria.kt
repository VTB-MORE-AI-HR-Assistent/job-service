package com.aristurtle.job_service.dto

data class VacancySearchCriteria(
    val status: String? = null,
    val region: String? = null,
    val city: String? = null,
    val address: String? = null,
    val workType: String? = null,
    val employmentType: String? = null,
    val income: Int? = null,
    val salaryMax: Int? = null,
    val salaryMin: Int? = null,
    val workSchedule: String? = null,
    val annualBonus: Int? = null,
    val bonusType: String? = null,
    val responsibilities: List<String> = emptyList(),
    val requirements: List<String> = emptyList(),
    val educationType: String? = null,
    val experienceFrom: Int? = null,
    val experienceTo: Int? = null,
    val knowledgeLanguages: List<String> = emptyList(),
    val levelLanguages: List<String> = emptyList(),
    val businessTrips: Boolean? = null,
    val additionalInfo: String? = null,
    val programRequirements: List<String> = emptyList()
)