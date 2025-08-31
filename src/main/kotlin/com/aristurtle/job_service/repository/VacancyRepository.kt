package com.aristurtle.job_service.repository

import com.aristurtle.job_service.model.Vacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VacancyRepository : JpaRepository<Vacancy, Int>, JpaSpecificationExecutor<Vacancy> {

    @Query(
        """
    SELECT * FROM jobs.vacancies v 
    WHERE (:title IS NULL OR LOWER(v.title) LIKE LOWER('%' || :title || '%')) 
    AND (:company IS NULL OR LOWER(v.company) LIKE LOWER('%' || :company || '%')) 
    AND (:location IS NULL OR LOWER(v.location) LIKE LOWER('%' || :location || '%')) 
    AND (:isRemote IS NULL OR v.is_remote = :isRemote) 
    AND v.status = 'active' 
    ORDER BY v.created_at DESC
""", nativeQuery = true
    )
    fun findByFilters(
        @Param("title") title: String?,
        @Param("company") company: String?,
        @Param("location") location: String?,
        @Param("isRemote") isRemote: Boolean?
    ): List<Vacancy>

    fun findByStatusOrderByCreatedAtDesc(status: String): List<Vacancy>

    @Query("SELECT v FROM Vacancy v WHERE v.salaryFrom >= :minSalary AND v.status = 'active'")
    fun findByMinSalary(@Param("minSalary") minSalary: Int): List<Vacancy>

    fun findByIsRemoteAndStatus(isRemote: Boolean, status: String): List<Vacancy>
}