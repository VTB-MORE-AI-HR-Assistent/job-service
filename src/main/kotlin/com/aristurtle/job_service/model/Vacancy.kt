package com.aristurtle.job_service.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "vacancies", schema = "jobs")
data class Vacancy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "title", nullable = false, length = 255)
    val title: String,

    @Column(name = "company", nullable = false, length = 255)
    val company: String,

    @Column(name = "description", columnDefinition = "TEXT")
    val description: String? = null,

    @Column(name = "requirements", columnDefinition = "TEXT")
    val requirements: String? = null,

    @Column(name = "contacts", columnDefinition = "TEXT")
    val contacts: String? = null,

    @Column(name = "salary_from")
    val salaryFrom: Int? = null,

    @Column(name = "salary_to")
    val salaryTo: Int? = null,

    @Column(name = "salary_currency", length = 10)
    val salaryCurrency: String? = null,

    @Column(name = "salary_gross")
    val salaryGross: Boolean? = null,

    @Column(name = "experience", length = 50)
    val experience: String? = null,

    @Column(name = "employment_type", length = 50)
    val employmentType: String? = null,

    @Column(name = "work_schedule", length = 50)
    val workSchedule: String? = null,

    @Column(name = "location", length = 255)
    val location: String? = null,

    @Column(name = "is_remote")
    val isRemote: Boolean = false,

    @Column(name = "status", length = 50)
    val status: String = "active",

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)