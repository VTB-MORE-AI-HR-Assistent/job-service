package com.aristurtle.job_service.repository

import com.aristurtle.job_service.model.Vacancy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VacancyRepository :
    PagingAndSortingRepository<Vacancy, Long>,
    JpaRepository<Vacancy, Long>,
    JpaSpecificationExecutor<Vacancy>
{
//    fun findByRequest(request: VacancyRequest): List<Vacancy>
//
//    fun findByRequest(request: VacancyRequest, pageable: Pageable): List<Vacancy>

    @Query(
        """
        SELECT v FROM Vacancy v WHERE
        (:status IS NULL OR v.status = :status) AND
        (:region IS NULL OR v.region = :region) AND
        (:city IS NULL OR v.city = :city) AND
        (:minSalary IS NULL OR v.salaryMax >= :minSalary) AND
        (:maxSalary IS NULL OR v.salaryMin <= :maxSalary)
    """
    )
    fun findWithFilters(
        @Param("status") status: String?,
        @Param("region") region: String?,
        @Param("city") city: String?,
        @Param("minSalary") minSalary: Int?,
        @Param("maxSalary") maxSalary: Int?,
        pageable: Pageable
    ): Page<Vacancy>

    @Query("SELECT v.status, COUNT(v) FROM Vacancy v GROUP BY v.status")
    fun countByStatus(): Map<String, Long>

    @Query("SELECT v.region, COUNT(v) FROM Vacancy v GROUP BY v.region")
    fun countByRegion(): Map<String, Long>
}