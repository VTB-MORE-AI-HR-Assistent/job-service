package com.aristurtle.job_service.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class VacancyTest {
    
    @Test
    fun `create vacancy with required fields`() {
        val vacancy = Vacancy(
            title = "Java Developer",
            company = "Tech Corp"
        )
        
        assertEquals("Java Developer", vacancy.title)
        assertEquals("Tech Corp", vacancy.company)
        assertEquals("active", vacancy.status)
        assertFalse(vacancy.isRemote)
        assertNotNull(vacancy.createdAt)
        assertNotNull(vacancy.updatedAt)
    }
    
    @Test
    fun `create vacancy with all fields`() {
        val now = LocalDateTime.now()
        val vacancy = Vacancy(
            id = 1,
            title = "Kotlin Developer",
            company = "Startup Inc",
            description = "Разработка backend на Kotlin",
            requirements = "Опыт работы с Spring Boot, Kotlin",
            contacts = "hr@startup.com",
            salaryFrom = 150000,
            salaryTo = 250000,
            salaryCurrency = "RUB",
            salaryGross = true,
            experience = "от 3 лет",
            employmentType = "полная",
            workSchedule = "удаленка",
            location = "Москва",
            isRemote = true,
            status = "active",
            createdAt = now,
            updatedAt = now
        )
        
        assertEquals(1, vacancy.id)
        assertEquals("Kotlin Developer", vacancy.title)
        assertEquals(150000, vacancy.salaryFrom)
        assertEquals(250000, vacancy.salaryTo)
        assertEquals("RUB", vacancy.salaryCurrency)
        assertTrue(vacancy.salaryGross!!)
        assertTrue(vacancy.isRemote)
        assertEquals(now, vacancy.createdAt)
    }
    
    @Test
    fun `test equals and hashCode`() {
        val vacancy1 = Vacancy(id = 1, title = "Dev", company = "Company")
        val vacancy2 = Vacancy(id = 1, title = "Dev", company = "Company")
        val vacancy3 = Vacancy(id = 2, title = "Dev", company = "Company")
        
        assertEquals(vacancy1, vacancy2)
        assertNotEquals(vacancy1, vacancy3)
        assertEquals(vacancy1.hashCode(), vacancy2.hashCode())
    }
    
    @Test
    fun `test toString contains important fields`() {
        val vacancy = Vacancy(id = 1, title = "Developer", company = "Company")
        val toString = vacancy.toString()
        
        assertTrue(toString.contains("Developer"))
        assertTrue(toString.contains("Company"))
    }
}