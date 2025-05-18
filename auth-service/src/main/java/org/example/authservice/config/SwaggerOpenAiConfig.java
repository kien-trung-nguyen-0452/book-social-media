package org.example.authservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "auth-service api",
                version = "1.0",
                description = "API documentation for the store management application"

        ),
        servers = @Server(url = "http://localhost:8080/identity")
)
public class SwaggerOpenAiConfig {
}
