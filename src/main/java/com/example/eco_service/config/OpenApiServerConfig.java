package com.example.eco_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Явно задаёт server url «/», чтобы Swagger UI не подставлял неверный абсолютный URL
 * и запросы «Try it out» шли на тот же хост/схему, что и страница Swagger.
 */
@Configuration
public class OpenApiServerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("/").description("Текущий сервер")));
    }
}
