package com.aristurtle.job_service.service.impl

import com.aristurtle.job_service.model.Vacancy
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.ai.document.Document
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.slf4j.LoggerFactory

@Service
class VacancyParserService(
    private val chatModel: OpenAiChatModel
) {

    private val logger = LoggerFactory.getLogger(VacancyParserService::class.java)

    fun parseVacancyFromPdf(pdfFile: MultipartFile): Vacancy {
        logger.info("Parsing PDF file: ${pdfFile.originalFilename}, size: ${pdfFile.size}")

        // Читаем PDF напрямую из MultipartFile
        val pdfBytes = pdfFile.bytes

        // Используем ByteArrayResource
        val pdfResource = ByteArrayResource(pdfBytes)

        val pdfReader = PagePdfDocumentReader(
            pdfResource,
            PdfDocumentReaderConfig.defaultConfig()
        )

        val documents = pdfReader.get()
        val pdfText = extractTextFromDocuments(documents)

        logger.info("Extracted text length: ${pdfText.length}")

        // Парсим с помощью OpenAI
        return parseWithOpenAI(pdfText)
    }

    private fun extractTextFromDocuments(documents: List<Document>): String {
        return documents.joinToString("\n") { document ->
            // В новых версиях Spring AI используется getContent() вместо content
            document.text as CharSequence // или document.text в некоторых версиях
        }
    }

    private fun parseWithOpenAI(pdfText: String): Vacancy {
        val prompt = """
            Проанализируй текст вакансии и извлеки информацию в формате JSON.
            Текст вакансии:
            ${pdfText.take(8000)} <!-- Ограничиваем размер для токенов -->
            
            Верни ТОЛЬКО JSON объект со следующей структурой:
            {
                "status": "string (активная/неактивная)",
                "region": "string",
                "city": "string", 
                "address": "string",
                "workType": "string (офис/удалёнка/гибрид)",
                "positionTitle": "string",
                "employmentType": "string (полная/частичная/проектная)",
                "income": "number (общий доход)",
                "salaryMin": "number",
                "salaryMax": "number",
                "workSchedule": "string (график работы)",
                "annualBonus": "number",
                "bonusType": "string",
                "responsibilities": ["string", "string"],
                "requirements": ["string", "string"],
                "educationType": "string",
                "experienceFrom": "number",
                "experienceTo": "number", 
                "knowledgeLanguages": ["string"],
                "levelLanguages": ["string"],
                "businessTrips": "boolean",
                "additionalInfo": "string",
                "programRequirements": ["string"]
            }
            
            Важные правила:
            1. Если информация не найдена, используй null
            2. Для числовых полей извлекай только числа
            3. Для массивов создавай список, даже если один элемент
            4. Для boolean полей: true/false или null
            5. Верни ТОЛЬКО JSON без каких-либо дополнительных текстов
        """.trimIndent()

        val response = chatModel.call(prompt)
        logger.info("OpenAI response: $response")

        return parseJsonResponse(response)
    }

    private fun parseJsonResponse(jsonResponse: String): Vacancy {
        val objectMapper = ObjectMapper().apply {
            registerModule(com.fasterxml.jackson.module.kotlin.kotlinModule())
        }

        // Очищаем ответ от возможных не-JSON частей
        val cleanJson = jsonResponse
            .replaceFirst("```json", "")
            .replaceFirst("```", "")
            .trim()

        return try {
            objectMapper.readValue(cleanJson, Vacancy::class.java)
        } catch (e: Exception) {
            logger.error("Error parsing JSON response: $jsonResponse", e)
            throw RuntimeException("Failed to parse OpenAI response: ${e.message}")
        }
    }
}