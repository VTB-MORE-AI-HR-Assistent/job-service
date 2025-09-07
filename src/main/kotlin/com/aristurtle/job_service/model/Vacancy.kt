package com.aristurtle.job_service.model

import jakarta.persistence.*

import java.time.OffsetDateTime

@Entity
@Table(name = "vacancies")
data class Vacancy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "status", length = 50)
    val status: String? = null,

    @Column(name = "region", length = 100)
    val region: String? = null,

    @Column(name = "city", length = 100)
    val city: String? = null,

    @Column(name = "address", length = 255)
    val address: String? = null,

    @Column(name = "work_type", length = 100)
    val workType: String? = null,

    @Column(name = "position_title")
    val positionTitle: String? = null,

    @Column(name = "employment_type", length = 100)
    val employmentType: String? = null,

    @Column(name = "income")
    val income: Int? = null,

    @Column(name = "salary_max")
    val salaryMax: Int? = null,

    @Column(name = "salary_min")
    val salaryMin: Int? = null,

    @Column(name = "work_schedule", length = 100)
    val workSchedule: String? = null,

    @Column(name = "annual_bonus")
    val annualBonus: Int? = null,

    @Column(name = "bonus_type", length = 50)
    val bonusType: String? = null,

    @Column(name = "responsibilities", columnDefinition = "TEXT[]")
    val responsibilities: List<String> = emptyList(),

    @Column(name = "requirements", columnDefinition = "TEXT[]")
    val requirements: List<String> = emptyList(),

    @Column(name = "education_type", length = 100)
    val educationType: String? = null,

    @Column(name = "experience_from")
    val experienceFrom: Int? = null,

    @Column(name = "experience_to")
    val experienceTo: Int? = null,

    @Column(name = "knowledge_languages", columnDefinition = "VARCHAR(50)[]")
    val knowledgeLanguages: List<String>? = emptyList(),

    @Column(name = "level_languages", columnDefinition = "VARCHAR(50)[]")
    val levelLanguages: List<String>? = emptyList(),

    @Column(name = "business_trips")
    val businessTrips: Boolean? = null,

    @Column(name = "additional_info", columnDefinition = "TEXT")
    val additionalInfo: String? = null,

    @Column(name = "program_requirements", columnDefinition = "TEXT[]")
    val programRequirements: List<String>? = emptyList(),

    @Column(name = "creation_dts", nullable = false)
    val creationDts: OffsetDateTime = OffsetDateTime.now()
)