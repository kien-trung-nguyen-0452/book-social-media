package org.example.crawlservice.repository;


import org.example.crawlservice.config.FeignClientConfig;
import org.example.crawlservice.dto.data.BookInfo;
import org.example.crawlservice.dto.request.BookRequest;
import org.example.crawlservice.dto.response.ApiResponse;
import org.example.crawlservice.dto.response.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-service",configuration = FeignClientConfig.class)
public interface BookClient {
    @PostMapping("book/internal/create")
    ApiResponse<BookResponse> createBook(@RequestBody BookRequest request);
}
