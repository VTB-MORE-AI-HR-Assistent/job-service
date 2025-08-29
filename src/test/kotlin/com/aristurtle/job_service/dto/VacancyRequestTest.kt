package com.aristurtle.job_service.dto

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import jakarta.validation.Validation
import jakarta.validation.Validator

class VacancyRequestTest {
    
    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator
    
    @Test
    fun `valid vacancy request should pass validation`() {
        val request = VacancyRequest(
            title = "Valid Title",
            company = "Valid Company",
            salaryFrom = 100000,
            salaryTo = 200000,
            salaryCurrency = "RUB",
            isRemote = true
        )
        
        val violations = validator.validate(request)
        assertTrue(violations.isEmpty())
    }
    
    @Test
    fun `invalid vacancy request should fail validation`() {
        val request = VacancyRequest(
            title = "", // invalid - empty
            company = "A".repeat(256), // invalid - too long
            salaryFrom = -1000, // invalid - negative
            salaryCurrency = "INVALID" // invalid currency
        )
        
        val violations = validator.validate(request)
        assertEquals(4, violations.size)
    }
    
    @Test
    fun `salary validation - from less than to`() {
        val request = VacancyRequest(
            title = "Title",
            company = "Company",
            salaryFrom = 200000,
            salaryTo = 100000 // invalid - from > to
        )
        
        val violations = validator.validate(request)
        assertTrue(violations.any { it.message == "Salary from cannot be greater than salary to" })
    }
    
    @Test
    fun `currency validation with valid values`() {
        val validCurrencies = listOf("RUB", "USD", "EUR", "KZT", null, "")
        
        validCurrencies.forEach { currency ->
            val request = VacancyRequest(
                title = "Title",
                company = "Company",
                salaryCurrency = currency
            )
            
            val violations = validator.validate(request)
            assertTrue(violations.none { it.propertyPath.toString() == "salaryCurrency" })
        }
    }
}