package com.aristurtle.job_service.service.impl

import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.exception.VacancyNotFoundException
import com.aristurtle.job_service.mapper.VacancyMapper
import com.aristurtle.job_service.model.Vacancy
import com.aristurtle.job_service.repository.VacancyRepository
import com.aristurtle.job_service.service.VacancyService
import org.springframework.stereotype.Service

@Service
class VacancyServiceImpl(
    private val vacancyRepository: VacancyRepository
) : VacancyService {
    
    override fun getAllVacancies(): List<Vacancy> {
        return vacancyRepository.findAll()
    }
    
    override fun getVacancyById(id: Int): Vacancy {
        return vacancyRepository.findById(id)
            .orElseThrow { VacancyNotFoundException("Vacancy with id $id not found") }
    }
    
    override fun createVacancy(vacancy: Vacancy): Vacancy {
        return vacancyRepository.save(vacancy)
    }
    
    override fun updateVacancy(id: Int, request: VacancyRequest): Vacancy {
        val existingVacancy = getVacancyById(id)
        val updatedVacancy = VacancyMapper.updateEntityFromRequest(existingVacancy, request)
        return vacancyRepository.save(updatedVacancy)
    }
    
    override fun deleteVacancy(id: Int) {
        if (!vacancyRepository.existsById(id))
            throw VacancyNotFoundException("Vacancy with id $id not found")
        vacancyRepository.deleteById(id)
    }

    override fun searchVacancies(
        title: String?,
        company: String?,
        location: String?,
        isRemote: Boolean?
    ): List<Vacancy> {
        return vacancyRepository.findByFilters(title, company, location, isRemote)
    }
}