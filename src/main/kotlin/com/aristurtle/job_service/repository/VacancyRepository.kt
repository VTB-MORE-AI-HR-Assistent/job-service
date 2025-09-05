package com.aristurtle.job_service.repository

import com.aristurtle.job_service.model.Vacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface VacancyRepository :
    PagingAndSortingRepository<Vacancy, Long>,
    JpaRepository<Vacancy, Long>,
    JpaSpecificationExecutor<Vacancy>