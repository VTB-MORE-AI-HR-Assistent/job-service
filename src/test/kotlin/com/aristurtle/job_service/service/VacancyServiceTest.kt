package com.aristurtle.job_service.service

import com.aristurtle.job_service.dto.VacancyRequest
import com.aristurtle.job_service.exception.VacancyNotFoundException
import com.aristurtle.job_service.model.Vacancy
import com.aristurtle.job_service.repository.VacancyRepository
import com.aristurtle.job_service.service.impl.VacancyServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.util.*

@ExtendWith(MockitoExtension::class)
class VacancyServiceTest {
    
    @Mock
    private lateinit var vacancyRepository: VacancyRepository
    
    private lateinit var vacancyService: VacancyService
    
    private lateinit var testVacancy: Vacancy
    
    @BeforeEach
    fun setUp() {
        vacancyService = VacancyServiceImpl(vacancyRepository)
        testVacancy = Vacancy(
            id = 1,
            title = "Test Developer",
            company = "Test Company",
            status = "active"
        )
    }
    
    @Test
    fun `getAllVacancies should return all vacancies`() {
        val vacancies = listOf(testVacancy, testVacancy.copy(id = 2))
        whenever(vacancyRepository.findAll()).thenReturn(vacancies)
        
        val result = vacancyService.getAllVacancies()
        
        assertEquals(2, result.size)
        verify(vacancyRepository).findAll()
    }
    
    @Test
    fun `getVacancyById should return vacancy when exists`() {
        whenever(vacancyRepository.findById(1)).thenReturn(Optional.of(testVacancy))
        
        val result = vacancyService.getVacancyById(1)
        
        assertEquals("Test Developer", result.title)
        verify(vacancyRepository).findById(1)
    }
    
    @Test
    fun `getVacancyById should throw exception when not exists`() {
        whenever(vacancyRepository.findById(999)).thenReturn(Optional.empty())
        
        assertThrows<VacancyNotFoundException> {
            vacancyService.getVacancyById(999)
        }
    }
    
    @Test
    fun `createVacancy should save and return vacancy`() {
        whenever(vacancyRepository.save(any())).thenReturn(testVacancy)
        
        val result = vacancyService.createVacancy(testVacancy)
        
        assertEquals("Test Developer", result.title)
        verify(vacancyRepository).save(testVacancy)
    }
    
    @Test
    fun `updateVacancy should update existing vacancy`() {
        val request = VacancyRequest(
            title = "Updated Title",
            company = "Updated Company",
            status = "on_pause"
        )
        
        whenever(vacancyRepository.findById(1)).thenReturn(Optional.of(testVacancy))
        whenever(vacancyRepository.save(any())).thenAnswer { it.arguments[0] }
        
        val result = vacancyService.updateVacancy(1, request)
        
        assertEquals("Updated Title", result.title)
        assertEquals("Updated Company", result.company)
        assertEquals("on_pause", result.status)
        assertTrue(result.updatedAt.isAfter(testVacancy.updatedAt))
    }
    
    @Test
    fun `deleteVacancy should delete when exists`() {
        whenever(vacancyRepository.existsById(1)).thenReturn(true)
        
        vacancyService.deleteVacancy(1)
        
        verify(vacancyRepository).deleteById(1)
    }
    
    @Test
    fun `deleteVacancy should throw exception when not exists`() {
        whenever(vacancyRepository.existsById(999)).thenReturn(false)
        
        assertThrows<VacancyNotFoundException> {
            vacancyService.deleteVacancy(999)
        }
        
        verify(vacancyRepository, never()).deleteById(any())
    }
}