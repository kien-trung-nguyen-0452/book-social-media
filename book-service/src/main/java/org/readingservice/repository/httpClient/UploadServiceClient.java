package org.readingservice.repository.httpClient;

import org.readingservice.repository.httpClient.dto.upload.UploadResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "upload-service")
public interface UploadServiceClient {


    @GetMapping("/uploads/chapter/{chapterId}")
    ApiResponse<List<UploadResponse>> getImagesByChapterId(@PathVariable("chapterId") Long chapterId);
}
