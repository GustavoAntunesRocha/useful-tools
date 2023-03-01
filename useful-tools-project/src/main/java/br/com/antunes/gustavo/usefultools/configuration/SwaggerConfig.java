package br.com.antunes.gustavo.usefultools.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI baseOpenAPI() {
    	return new OpenAPI().info(new Info().title("Useful tools to remember API").version("1.0.0").description("API to manage useful tools"));
    }
}

