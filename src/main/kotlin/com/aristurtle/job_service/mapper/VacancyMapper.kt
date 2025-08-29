package com.aristurtle.job_service.mapper

import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.dto.VacancyResponse
import com.aristurtle.job_service.dto.VacancyShortResponse
import com.aristurtle.job_service.model.Vacancy
import java.time.LocalDateTime

object VacancyMapper {
    
    fun toEntity(request: VacancyRequest): Vacancy {
        return Vacancy(
            title = request.title,
            company = request.company,
            description = request.description,
            requirements = request.requirements,
            contacts = request.contacts,
            salaryFrom = request.salaryFrom,
            salaryTo = request.salaryTo,
            salaryCurrency = request.salaryCurrency,
            salaryGross = request.salaryGross,
            experience = request.experience,
            employmentType = request.employmentType,
            workSchedule = request.workSchedule,
            location = request.location,
            isRemote = request.isRemote,
            status = request.status ?: "active"
        )
    }
    
    fun toResponse(entity: Vacancy): VacancyResponse {
        return VacancyResponse(
            id = entity.id!!,
            title = entity.title,
            company = entity.company,
            description = entity.description,
            requirements = entity.requirements,
            contacts = entity.contacts,
            salaryFrom = entity.salaryFrom,
            salaryTo = entity.salaryTo,
            salaryCurrency = entity.salaryCurrency,
            salaryGross = entity.salaryGross,
            experience = entity.experience,
            employmentType = entity.employmentType,
            workSchedule = entity.workSchedule,
            location = entity.location,
            isRemote = entity.isRemote,
            status = entity.status,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    fun toShortResponse(entity: Vacancy): VacancyShortResponse {
        return VacancyShortResponse(
            id = entity.id!!,
            title = entity.title,
            company = entity.company,
            salaryFrom = entity.salaryFrom,
            salaryTo = entity.salaryTo,
            salaryCurrency = entity.salaryCurrency,
            location = entity.location,
            isRemote = entity.isRemote,
            status = entity.status,
            createdAt = entity.createdAt
        )
    }
    
    fun updateEntityFromRequest(entity: Vacancy, request: VacancyRequest): Vacancy {
        return entity.copy(
            title = request.title,
            company = request.company,
            description = request.description,
            requirements = request.requirements,
            contacts = request.contacts,
            salaryFrom = request.salaryFrom,
            salaryTo = request.salaryTo,
            salaryCurrency = request.salaryCurrency,
            salaryGross = request.salaryGross,
            experience = request.experience,
            employmentType = request.employmentType,
            workSchedule = request.workSchedule,
            location = request.location,
            isRemote = request.isRemote,
            status = request.status ?: entity.status,
            updatedAt = LocalDateTime.now()
        )
    }
}