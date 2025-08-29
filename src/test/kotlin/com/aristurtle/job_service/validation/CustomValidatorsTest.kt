package com.aristurtle.job_service.validation

import com.aristurtle.job_service.dto.VacancyRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import jakarta.validation.Validation

class CustomValidatorsTest {
    
    private val validator = Validation.buildDefaultValidatorFactory().validator
    
    @Test
    fun `SalaryValidation should pass when from less than to`() {
        val request = VacancyRequest(
            title = "Title",
            company = "Company",
            salaryFrom = 100000,
            salaryTo = 200000
        )
        
        val violations = validator.validate(request)
        assertTrue(violations.none { it.message == "Salary from cannot be greater than salary to" })
    }
    
    @Test
    fun `SalaryValidation should fail when from greater than to`() {
        val request = VacancyRequest(
            title = "Title",
            company = "Company",
            salaryFrom = 200000,
            salaryTo = 100000
        )
        
        val violations = validator.validate(request)
        assertTrue(violations.any { it.message == "Salary from cannot be greater than salary to" })
    }
    
    @Test
    fun `ValidCurrency should accept valid currencies`() {
        listOf("RUB", "USD", "EUR", "KZT", null, "").forEach { currency ->
            val request = VacancyRequest(
                title = "Title",
                company = "Company",
                salaryCurrency = currency
            )
            
            val violations = validator.validate(request)
            assertTrue(violations.none { it.propertyPath.toString() == "salaryCurrency" })
        }
    }
    
    @Test
    fun `ValidCurrency should reject invalid currencies`() {
        val request = VacancyRequest(
            title = "Title",
            company = "Company",
            salaryCurrency = "INVALID"
        )
        
        val violations = validator.validate(request)
        assertTrue(violations.any { it.propertyPath.toString() == "salaryCurrency" })
    }
}