package com.aristurtle.job_service.config

import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Vacancies API")
                .version("1.0")
                .description("API для управления вакансиями")
                .contact(Contact()
                    .name("Support Team")
                    .email("support@vacancies.com"))
                .license(License()
                    .name("Apache 2.0")
                    .url("http://springdoc.org")))
            .components(Components()
                .addSchemas("LocalDateTime", StringSchema().example("2024-01-15T10:30:00")))
    }

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("public")
            .pathsToMatch("/api/v1/vacancies/**")
            .addOpenApiCustomizer(globalHeadersCustomizer())
            .build()
    }

    @Bean
    fun globalHeadersCustomizer(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi ->
            openApi.components.addParameters("Accept-Language", Parameter()
                .`in`(ParameterIn.HEADER.toString())
                .name("Accept-Language")
                .description("Язык ответа (ru, en, etc.)")
                .schema(StringSchema().example("ru")))
        }
    }
}