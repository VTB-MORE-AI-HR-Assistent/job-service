package com.aristurtle.job_service.service

import com.aristurtle.job_service.service.impl.VacancyParserService
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import kotlin.test.Test

@SpringBootTest
class VacancyParserServiceTest {

    @Autowired
    private lateinit var vacancyParserService: VacancyParserService

    @Test
    fun testPdfParsing() {
        val pdfFile = MockMultipartFile(
            "vac.pdf",
            "vac.pdf",
            "application/pdf",
            javaClass.classLoader.getResourceAsStream("vacancies/vac.pdf") ?: throw RuntimeException("test pdf file not found")
        )

        val vacancy = vacancyParserService.parseVacancyFromPdf(pdfFile)

        assertNotNull(vacancy.positionTitle)
        assertNotNull(vacancy.requirements)
        println("Parsed vacancy: $vacancy")
    }
}