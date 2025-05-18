package org.chapterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@EnableFeignClients
@EnableScheduling
public class ChapterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChapterServiceApplication.class, args);
    }
}
