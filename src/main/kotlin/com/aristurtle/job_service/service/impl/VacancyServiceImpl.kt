package com.aristurtle.job_service.service.impl

import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.model.Vacancy
import com.aristurtle.job_service.repository.VacancyRepository
import com.aristurtle.job_service.service.VacancyService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class VacancyServiceImpl(
    private val vacancyRepository: VacancyRepository
) : VacancyService {
    override fun getAll(): List<Vacancy> =
        vacancyRepository.findAll()

    override fun getAll(pageable: Pageable): Page<Vacancy> =
        vacancyRepository.findAll(pageable)

    override fun getVacancyById(id: Long): Optional<Vacancy> =
        vacancyRepository.findById(id)

    override fun createVacancy(vacancy: Vacancy): Vacancy =
        vacancyRepository.save(vacancy.copy(creationDts = OffsetDateTime.now()))

    override fun updateVacancy(id: Long, vacancy: Vacancy): Vacancy {
        val existingVacancy = vacancyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Vacancy with id $id not found") }

        val updatedVacancy = existingVacancy.copy(
            status = vacancy.status,
            region = vacancy.region,
            city = vacancy.city,
            address = vacancy.address,
            workType = vacancy.workType,
            employmentType = vacancy.employmentType,
            income = vacancy.income,
            salaryMax = vacancy.salaryMax,
            salaryMin = vacancy.salaryMin,
            workSchedule = vacancy.workSchedule,
            annualBonus = vacancy.annualBonus,
            bonusType = vacancy.bonusType,
            responsibilities = vacancy.responsibilities,
            requirements = vacancy.requirements,
            educationType = vacancy.educationType,
            experienceFrom = vacancy.experienceFrom,
            experienceTo = vacancy.experienceTo,
            knowledgeLanguages = vacancy.knowledgeLanguages,
            levelLanguages = vacancy.levelLanguages,
            businessTrips = vacancy.businessTrips,
            additionalInfo = vacancy.additionalInfo,
            programRequirements = vacancy.programRequirements
        )

        return vacancyRepository.save(updatedVacancy)
    }

    override fun deleteVacancy(id: Long): Boolean {
        return if (vacancyRepository.existsById(id)) {
            vacancyRepository.deleteById(id)
            true
        } else {
            false
        }
    }

//    override fun searchVacancies(
//        vacancyRequest: VacancyRequest
//    ): List<Vacancy> =
//        vacancyRepository.findByRequest(vacancyRequest)
//
//    override fun searchVacancies(
//        vacancyRequest: VacancyRequest,
//        pageable: Pageable
//    ): List<Vacancy> =
//        vacancyRepository.findByRequest(vacancyRequest, pageable)

    override fun getCountByStatus(): Map<String, Long> {
        return vacancyRepository.countByStatus()
    }

    override fun getCountByRegion(): Map<String, Long> {
        return vacancyRepository.countByRegion()
    }
}