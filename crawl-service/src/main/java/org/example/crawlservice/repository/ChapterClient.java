package org.example.crawlservice.repository;

import org.example.crawlservice.dto.request.ChapterRequest;
import org.example.crawlservice.dto.response.ChapterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "chapter-service")
public interface ChapterClient {
    @PostMapping("/chapters")
    ChapterResponse createChapter(@RequestBody ChapterRequest request);
}
