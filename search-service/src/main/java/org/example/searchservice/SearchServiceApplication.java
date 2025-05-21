package org.example.searchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableElasticsearchRepositories
@EnableMethodSecurity
@SpringBootApplication
@EnableFeignClients
public class SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }

}
