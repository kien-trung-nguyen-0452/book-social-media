package org.example.crawlservice.repository;

import org.example.crawlservice.dto.request.UploadRequest;
import org.example.crawlservice.dto.response.UploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "upload-service")
public interface UploadClient {
    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadResponse upload(@RequestParam("file") MultipartFile file);

    @PostMapping("/upload/from-url")
    UploadResponse uploadFromUrl(@RequestBody UploadRequest request);
}