package com.aristurtle.job_service.service.impl

import com.aristurtle.job_service.exception.AiParsingFailException
import com.aristurtle.job_service.exception.FileParsingFailException
import com.aristurtle.job_service.exception.UnsupportedFileTypeException
import com.aristurtle.job_service.model.Vacancy
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.reader.tika.TikaDocumentReader
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

private const val FILE_CONTENT_CHAR_LIMIT_AMOUNT = 12000

@Service
class VacancyParserService(
    private val chatModel: OpenAiChatModel
) {

    private val logger = LoggerFactory.getLogger(VacancyParserService::class.java)
    private val supportedMimeTypes = setOf(
        "application/pdf",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // DOCX
        "text/plain",
        "application/msword" // DOC (старый формат)
    )

    fun parseVacancyFromFile(file: MultipartFile): Vacancy {
        logger.info("Parsing file: ${file.originalFilename}, type: ${file.contentType}, size: ${file.size}")

        if (!isSupportedFileType(file))
            throw UnsupportedFileTypeException("Unsupported file type: ${file.contentType}. Supported types: $supportedMimeTypes")

        val fileText = when (file.contentType) {
            "application/pdf" -> extractTextFromPdf(file)
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> extractTextFromDocx(file)
            "application/msword" -> extractTextFromDoc(file) // Для старых .doc
            else -> extractTextWithTika(file) // Для текстовых файлов и других форматов
        }

        logger.info("Extracted text length: ${fileText.length}")
        logger.debug("Extracted text: $fileText")

        return parseWithOpenAI(fileText)
    }

    private fun isSupportedFileType(file: MultipartFile): Boolean {
        return supportedMimeTypes.contains(file.contentType?.lowercase())
    }

    private fun extractTextFromPdf(file: MultipartFile): String {
        val pdfBytes = file.bytes
        val pdfResource = ByteArrayResource(pdfBytes)

        val pdfReader = PagePdfDocumentReader(
            pdfResource,
            PdfDocumentReaderConfig.defaultConfig()
        )

        val documents = pdfReader.get()
        return extractTextFromDocuments(documents)
    }

    private fun extractTextFromDocx(file: MultipartFile): String {
        return try {
            val document = XWPFDocument(ByteArrayInputStream(file.bytes))
            val text = StringBuilder()

            // Читаем параграфы
            document.paragraphs.forEach { paragraph ->
                text.append(paragraph.text).append("\n")
            }

            // Читаем таблицы
            document.tables.forEach { table ->
                table.rows.forEach { row ->
                    row.tableCells.forEach { cell ->
                        text.append(cell.text).append("\t")
                    }
                    text.append("\n")
                }
            }

            document.close()
            text.toString()
        } catch (e: Exception) {
            logger.error("Error reading DOCX file", e)
            throw FileParsingFailException("Failed to read DOCX file: ${e.message}")
        }
    }

    private fun extractTextFromDoc(file: MultipartFile): String {
        // Для старых .doc файлов используем Tika
        return extractTextWithTika(file)
    }

    private fun extractTextWithTika(file: MultipartFile): String {
        return try {
            val tikaReader = TikaDocumentReader(ByteArrayResource(file.bytes))
            val documents = tikaReader.get()
            extractTextFromDocuments(documents)
        } catch (e: Exception) {
            logger.error("Error extracting text with Tika", e)
            throw FileParsingFailException("Failed to extract text from file: ${e.message}")
        }
    }

    private fun extractTextFromDocuments(documents: List<Document>): String {
        return documents.joinToString("\n") { document ->
            document.text as CharSequence
        }
    }

    private fun parseWithOpenAI(text: String): Vacancy {
        val prompt = """
            Проанализируй текст вакансии и извлеки информацию в формате JSON.
            Текст вакансии:
            ${text.take(FILE_CONTENT_CHAR_LIMIT_AMOUNT)}
            
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
            throw AiParsingFailException("Failed to parse OpenAI response: ${e.message}")
        }
    }
}