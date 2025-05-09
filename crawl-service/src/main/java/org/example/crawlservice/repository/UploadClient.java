package org.example.crawlservice.repository;

import org.example.crawlservice.dto.response.UploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "upload-service")
public interface UploadClient{
    @PostMapping("/upload/image")
    UploadResponse upload(@RequestBody String url);
}
