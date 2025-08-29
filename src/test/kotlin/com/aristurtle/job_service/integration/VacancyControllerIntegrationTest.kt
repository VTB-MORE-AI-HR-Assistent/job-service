package com.aristurtle.job_service.integration

import com.aristurtle.job_service.config.TestConfig
import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.model.Vacancy
import com.aristurtle.job_service.repository.VacancyRepository
import com.aristurtle.job_service.service.VacancyService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.mockito.Mock
import org.mockito.kotlin.*
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = [TestConfig::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class VacancyControllerIntegrationTest {
    
    @Autowired
    private lateinit var mockMvc: MockMvc
    
    @Mock
    private lateinit var vacancyRepository: VacancyRepository
    
    @Autowired
    private lateinit var vacancyService: VacancyService
    
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
    
    @Test
    fun `integration test for creating vacancy`() {
        val request = VacancyRequest(
            title = "Integration Test Developer",
            company = "Integration Test Company",
            salaryFrom = 100000,
            salaryTo = 200000,
            salaryCurrency = "RUB"
        )
        
        whenever(vacancyRepository.save(any())).thenAnswer { it.arguments[0] as Vacancy }
        
        mockMvc.perform(post("/api/v1/vacancies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.title").value("Integration Test Developer"))
            .andExpect(jsonPath("$.company").value("Integration Test Company"))
    }
}