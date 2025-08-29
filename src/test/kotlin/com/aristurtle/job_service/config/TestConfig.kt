package com.aristurtle.job_service.config

import com.aristurtle.job_service.mapper.VacancyMapper
import com.aristurtle.job_service.repository.VacancyRepository
import com.aristurtle.job_service.service.VacancyService
import com.aristurtle.job_service.service.impl.VacancyServiceImpl
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestConfig {
    
    @Bean
    fun vacancyService(vacancyRepository: VacancyRepository): VacancyService {
        return VacancyServiceImpl(vacancyRepository)
    }
    
    @Bean
    fun vacancyMapper(): VacancyMapper {
        return VacancyMapper
    }
}