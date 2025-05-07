package org.readingservice.client;

import org.readingservice.client.dto.upload.UploadResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "upload-service", url = "${service.upload.base-url}")
public interface UploadServiceClient {


    @GetMapping("/api/uploads/chapter/{chapterId}")
    ApiResponse<List<UploadResponse>> getImagesByChapterId(@PathVariable("chapterId") Long chapterId);
}
