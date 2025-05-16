package org.example.crawlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableMethodSecurity
public class CrawlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlServiceApplication.class, args);
    }

}
