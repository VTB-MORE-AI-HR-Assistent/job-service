package com.aristurtle.job_service.service.impl

import com.aristurtle.job_service.dto.VacancySearchCriteria
import com.aristurtle.job_service.model.Vacancy
import com.aristurtle.job_service.repository.VacancyRepository
import com.aristurtle.job_service.service.VacancyService
import com.aristurtle.job_service.util.VacancySpecifications
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
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

    override fun searchVacancies(vacancySearchCriteria: VacancySearchCriteria): List<Vacancy> {
        val specification = createSpecification(vacancySearchCriteria)
        return vacancyRepository.findAll(specification)
    }

    override fun searchVacancies(vacancySearchCriteria: VacancySearchCriteria, pageable: Pageable): List<Vacancy> {
        val specification = createSpecification(vacancySearchCriteria)
        return vacancyRepository.findAll(specification, pageable).toList()
    }

    private fun createSpecification(vacancySearchCriteria: VacancySearchCriteria): Specification<Vacancy> {
        return Specification.allOf(
            vacancySearchCriteria.status?.let { VacancySpecifications.hasStatus(it) },
            vacancySearchCriteria.region?.let { VacancySpecifications.hasRegion(it) },
            vacancySearchCriteria.city?.let { VacancySpecifications.hasCity(it) },
            vacancySearchCriteria.address?.let { VacancySpecifications.containsAddress(it) },
            vacancySearchCriteria.workType?.let { VacancySpecifications.hasWorkType(it) },
            vacancySearchCriteria.employmentType?.let { VacancySpecifications.hasEmploymentType(it) },
            vacancySearchCriteria.income?.let { VacancySpecifications.hasIncome(it) },
            vacancySearchCriteria.salaryMin?.let { VacancySpecifications.salaryGreaterThanOrEqual(it) },
            vacancySearchCriteria.salaryMax?.let { VacancySpecifications.salaryLessThanOrEqual(it) },
            vacancySearchCriteria.positionTitle?.let { VacancySpecifications.hasPositionTitle(it) },
            vacancySearchCriteria.workSchedule?.let { VacancySpecifications.hasWorkSchedule(it) },
            vacancySearchCriteria.annualBonus?.let { VacancySpecifications.hasAnnualBonus(it) },
            vacancySearchCriteria.bonusType?.let { VacancySpecifications.hasBonusType(it) },
            vacancySearchCriteria.educationType?.let { VacancySpecifications.hasEducationType(it) },
            vacancySearchCriteria.experienceFrom?.let { VacancySpecifications.experienceGreaterThanOrEqual(it) },
            vacancySearchCriteria.experienceTo?.let { VacancySpecifications.experienceLessThanOrEqual(it) },
            vacancySearchCriteria.businessTrips?.let { VacancySpecifications.hasBusinessTrips(it) },
            vacancySearchCriteria.additionalInfo?.let { VacancySpecifications.containsAdditionalInfo(it) },
            if (vacancySearchCriteria.responsibilities.isNotEmpty()) VacancySpecifications.hasAnyResponsibility(vacancySearchCriteria.responsibilities) else null,
            if (vacancySearchCriteria.requirements.isNotEmpty()) VacancySpecifications.hasAnyRequirement(vacancySearchCriteria.requirements) else null,
            if (vacancySearchCriteria.knowledgeLanguages.isNotEmpty()) VacancySpecifications.hasAnyKnowledgeLanguage(vacancySearchCriteria.knowledgeLanguages) else null,
            if (vacancySearchCriteria.levelLanguages.isNotEmpty()) VacancySpecifications.hasAnyLevelLanguage(vacancySearchCriteria.levelLanguages) else null,
            if (vacancySearchCriteria.programRequirements.isNotEmpty()) VacancySpecifications.hasAnyProgramRequirement(vacancySearchCriteria.programRequirements) else null
        )
    }
}