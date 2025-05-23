package org.chapterservice.repository;

import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.chapterservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    @GetMapping("book/internal/{bookId}/exists")
    ApiResponse<Boolean> checkBookExists(@PathVariable("bookId") String bookId);
}
