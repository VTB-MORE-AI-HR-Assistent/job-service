package com.aristurtle.job_service.validation

import com.aristurtle.job_service.dto.VacancyRequest
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SalaryValidator::class])
annotation class SalaryValidation(
    val message: String = "Salary from cannot be greater than salary to",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class SalaryValidator : ConstraintValidator<SalaryValidation, VacancyRequest> {
    override fun isValid(request: VacancyRequest, context: ConstraintValidatorContext): Boolean {
        return when {
            request.salaryFrom == null && request.salaryTo == null -> true
            request.salaryFrom != null && request.salaryTo == null -> true
            request.salaryFrom == null && request.salaryTo != null -> true
            else -> request.salaryFrom!! <= request.salaryTo!!
        }
    }
}