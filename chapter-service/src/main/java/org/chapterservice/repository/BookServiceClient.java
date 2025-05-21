package org.chapterservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    @GetMapping("book/internal/{bookId}/exists")
    Boolean checkBookExists(@PathVariable("bookId") String bookId);
}
