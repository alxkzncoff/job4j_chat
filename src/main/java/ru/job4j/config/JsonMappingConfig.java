package ru.job4j.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMappingConfig {


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
