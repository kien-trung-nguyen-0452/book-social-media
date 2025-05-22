package org.example.crawlservice.repository;

import org.example.crawlservice.config.FeignClientConfig;
import org.example.crawlservice.dto.request.ChapterRequest;
import org.example.crawlservice.dto.response.ApiResponse;
import org.example.crawlservice.dto.response.ChapterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "chapter-service", configuration = FeignClientConfig.class)
public interface ChapterClient {
    @PostMapping("chapter/internal/{bookId}/add")
    ApiResponse<ChapterResponse> createChapter(@PathVariable("bookId") String bookId, @RequestBody ChapterRequest request);
    @DeleteMapping("chapter/internal/delete/{id}")
    ApiResponse<ChapterResponse> deleteChapter(@PathVariable String id);
    @PutMapping("chapter/internal/update/{id}")
    ApiResponse<ChapterResponse> updateChapter(@PathVariable String id, @RequestBody ChapterRequest request);
}
