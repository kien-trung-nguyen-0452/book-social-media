package org.example.userprofileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableNeo4jRepositories
@EnableMethodSecurity
public class UserProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProfileServiceApplication.class, args);
    }

}
