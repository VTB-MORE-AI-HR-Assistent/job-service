package com.aristurtle.job_service.mapper

import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.model.Vacancy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class VacancyMapperTest {
    
    @Test
    fun `toEntity should map request to entity correctly`() {
        val request = VacancyRequest(
            title = "Developer",
            company = "Company",
            description = "Test description",
            salaryFrom = 100000,
            salaryTo = 200000,
            salaryCurrency = "RUB",
            isRemote = true,
            status = "active"
        )
        
        val entity = VacancyMapper.toEntity(request)
        
        assertEquals("Developer", entity.title)
        assertEquals("Company", entity.company)
        assertEquals("Test description", entity.description)
        assertEquals(100000, entity.salaryFrom)
        assertEquals(200000, entity.salaryTo)
        assertEquals("RUB", entity.salaryCurrency)
        assertTrue(entity.isRemote)
        assertEquals("active", entity.status)
        assertNull(entity.id)
        assertNotNull(entity.createdAt)
    }
    
    @Test
    fun `toResponse should map entity to response correctly`() {
        val now = LocalDateTime.now()
        val entity = Vacancy(
            id = 1,
            title = "Developer",
            company = "Company",
            description = "Desc",
            salaryFrom = 100000,
            salaryTo = 200000,
            salaryCurrency = "RUB",
            isRemote = true,
            status = "active",
            createdAt = now,
            updatedAt = now
        )
        
        val response = VacancyMapper.toResponse(entity)
        
        assertEquals(1, response.id)
        assertEquals("Developer", response.title)
        assertEquals(100000, response.salaryFrom)
        assertEquals(200000, response.salaryTo)
        assertEquals("RUB", response.salaryCurrency)
        assertTrue(response.isRemote)
        assertEquals(now, response.createdAt)
    }
    
    @Test
    fun `updateEntityFromRequest should update fields correctly`() {
        val original = Vacancy(
            id = 1,
            title = "Old Title",
            company = "Old Company",
            salaryFrom = 50000,
            status = "active",
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = LocalDateTime.now().minusDays(1)
        )
        
        val request = VacancyRequest(
            title = "New Title",
            company = "New Company",
            salaryFrom = 100000,
            salaryTo = 200000,
            status = "on_pause"
        )
        
        val updated = VacancyMapper.updateEntityFromRequest(original, request)
        
        assertEquals(1, updated.id)
        assertEquals("New Title", updated.title)
        assertEquals("New Company", updated.company)
        assertEquals(100000, updated.salaryFrom)
        assertEquals(200000, updated.salaryTo)
        assertEquals("on_pause", updated.status)
        assertEquals(original.createdAt, updated.createdAt)
        assertTrue(updated.updatedAt.isAfter(original.updatedAt))
    }
    
    @Test
    fun `toShortResponse should map only essential fields`() {
        val entity = Vacancy(
            id = 1,
            title = "Developer",
            company = "Company",
            salaryFrom = 100000,
            salaryTo = 200000,
            salaryCurrency = "RUB",
            location = "Moscow",
            isRemote = true,
            status = "active",
            createdAt = LocalDateTime.now()
        )
        
        val response = VacancyMapper.toShortResponse(entity)
        
        assertEquals(1, response.id)
        assertEquals("Developer", response.title)
        assertEquals("Company", response.company)
        assertEquals(100000, response.salaryFrom)
        assertEquals(200000, response.salaryTo)
        assertEquals("RUB", response.salaryCurrency)
        assertEquals("Moscow", response.location)
        assertTrue(response.isRemote)
        assertEquals("active", response.status)
        assertNotNull(response.createdAt)
    }
}