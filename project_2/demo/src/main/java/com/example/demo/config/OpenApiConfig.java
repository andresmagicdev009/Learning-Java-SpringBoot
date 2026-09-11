package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Global OpenAPI metadata shown at the top of Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI demoOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo User API")
                        .description("REST API for managing users")
                        .version("v1")
                        .contact(new Contact().name("Demo Team")));
    }
}
