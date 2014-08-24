package com.littlesquare.services.poc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

import javax.validation.constraints.NotNull

/**
 * @author Adam Jordens (adam@jordens.org)
 */
@Component
@ConfigurationProperties
class ApplicationConfiguration {
    @NotNull
    File scriptsDirectory

    /**
     * @return Custom object mapper (date formatting, etc.)
     */
    @Bean
    public ObjectMapper getObjectMapper() {
        def objectMapper = new ObjectMapper()
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        return objectMapper
    }
}
