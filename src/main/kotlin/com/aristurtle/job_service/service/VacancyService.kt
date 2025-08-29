package com.aristurtle.job_service.service

import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.model.Vacancy

interface VacancyService {
    fun getAllVacancies(): List<Vacancy>
    fun getVacancyById(id: Int): Vacancy
    fun createVacancy(vacancy: Vacancy): Vacancy
    fun updateVacancy(id: Int, request: VacancyRequest): Vacancy
    fun deleteVacancy(id: Int)
    fun searchVacancies(
        title: String?,
        company: String?,
        location: String?,
        isRemote: Boolean?
    ): List<Vacancy>
}