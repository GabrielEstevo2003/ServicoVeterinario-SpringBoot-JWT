package com.example.API_Animal.Configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Animais - Veterinario")
                        .version("1.0")
                        .description("Documentação da API responsável pela gestão de Animais no sistema de Veterinario."));
    }
}
