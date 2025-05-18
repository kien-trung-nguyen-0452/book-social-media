package org.example.crawlservice.repository;

import org.example.crawlservice.config.FeignClientConfig;
import org.example.crawlservice.dto.request.UploadRequest;
import org.example.crawlservice.dto.response.UploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.example.crawlservice.dto.response.ApiResponse;

@FeignClient(name = "upload-service", configuration = FeignClientConfig.class)
public interface UploadClient {
    @PostMapping(value = "upload/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<UploadResponse> upload(@RequestParam("file") MultipartFile file);

    @PostMapping("upload/upload/from-url")
    ApiResponse<UploadResponse> uploadFromUrl(@RequestBody UploadRequest request);
}
