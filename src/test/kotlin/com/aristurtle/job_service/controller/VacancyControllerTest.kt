package com.aristurtle.job_service.controller

import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.mapper.VacancyMapper
import com.aristurtle.job_service.model.Vacancy
import com.aristurtle.job_service.repository.VacancyRepository
import com.aristurtle.job_service.service.VacancyService
import com.aristurtle.job_service.service.impl.VacancyServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.*
import java.time.LocalDateTime
import java.util.Optional

@WebMvcTest(VacancyController::class)
@Import(VacancyServiceImpl::class, VacancyMapper::class)
@ExtendWith(MockitoExtension::class)
class VacancyControllerTest {
    
    @Autowired
    private lateinit var mockMvc: MockMvc
    
    @Mock
    private lateinit var vacancyRepository: VacancyRepository
    
    @Autowired
    private lateinit var vacancyService: VacancyService
    
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
    
    private val testVacancy = Vacancy(
        id = 1,
        title = "Test Developer",
        company = "Test Company",
        status = "active",
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
    
    @Test
    fun `getAllVacancies should return 200 with vacancies`() {
        whenever(vacancyRepository.findAll()).thenReturn(listOf(testVacancy))
        
        mockMvc.perform(get("/api/v1/vacancies"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title").value("Test Developer"))
            .andExpect(jsonPath("$[0].company").value("Test Company"))
    }
    
    @Test
    fun `getVacancyById should return 200 when exists`() {
        whenever(vacancyRepository.findById(1)).thenReturn(Optional.of(testVacancy))
        
        mockMvc.perform(get("/api/v1/vacancies/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Test Developer"))
    }
    
    @Test
    fun `getVacancyById should return 404 when not exists`() {
        whenever(vacancyRepository.findById(999)).thenReturn(Optional.empty())
        
        mockMvc.perform(get("/api/v1/vacancies/999"))
            .andExpect(status().isNotFound)
    }
    
    @Test
    fun `createVacancy should return 201 with created vacancy`() {
        val request = VacancyRequest(
            title = "New Developer",
            company = "New Company"
        )
        
        whenever(vacancyRepository.save(any())).thenReturn(testVacancy)
        
        mockMvc.perform(post("/api/v1/vacancies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.title").value("Test Developer"))
    }
    
    @Test
    fun `createVacancy should return 400 for invalid request`() {
        val invalidRequest = VacancyRequest(
            title = "", // invalid
            company = "Company"
        )
        
        mockMvc.perform(post("/api/v1/vacancies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest)
    }
    
    @Test
    fun `deleteVacancy should return 204`() {
        whenever(vacancyRepository.existsById(1)).thenReturn(true)
        
        mockMvc.perform(delete("/api/v1/vacancies/1"))
            .andExpect(status().isNoContent)
    }
}