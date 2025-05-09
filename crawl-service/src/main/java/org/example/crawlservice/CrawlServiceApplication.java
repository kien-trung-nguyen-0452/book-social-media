package org.example.crawlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CrawlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlServiceApplication.class, args);
    }

}
