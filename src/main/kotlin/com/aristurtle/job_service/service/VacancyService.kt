package com.aristurtle.job_service.service

import com.aristurtle.job_service.model.Vacancy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface VacancyService {
    fun getAll(): List<Vacancy>
    fun getAll(pageable: Pageable): Page<Vacancy>
    fun getVacancyById(id: Long): Optional<Vacancy>
    fun createVacancy(vacancy: Vacancy): Vacancy
    fun updateVacancy(id: Long, vacancy: Vacancy): Vacancy
    fun deleteVacancy(id: Long): Boolean
//    fun searchVacancies(vacancyRequest: VacancyRequest): List<Vacancy>
//    fun searchVacancies(vacancyRequest: VacancyRequest, pageable: Pageable): List<Vacancy>
    fun getCountByStatus(): Map<String, Long>
    fun getCountByRegion(): Map<String, Long>
}