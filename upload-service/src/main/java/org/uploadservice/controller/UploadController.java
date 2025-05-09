package org.uploadservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.uploadservice.dto.common.ApiResponse;
import org.uploadservice.dto.request.FromUrlUploadRequest;
import org.uploadservice.dto.response.FromUrlUploadResponse;
import org.uploadservice.service.UploadService;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = uploadService.uploadImage(file);
        return ApiResponse.<String>builder()
                .data(url)
                .message("Image uploaded successfully")
                .status(200)
                .build();
    }

    @PostMapping("/from-url")
    public ApiResponse<FromUrlUploadResponse> uploadFromUrl(@RequestBody FromUrlUploadRequest request){

        FromUrlUploadResponse response = uploadService.uploadChapterImage(
                request.getUrl(),
                request.getBookId(),
                request.getChapterId(),
                request.getName()
        );
        return ApiResponse.<FromUrlUploadResponse>builder().
                data(response)
                .build();

    }
}
