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
                .code(1000)
                .build();
    }

    @PostMapping("/from-url")
    public ApiResponse<FromUrlUploadResponse> uploadFromUrl(@RequestBody FromUrlUploadRequest request) {
        FromUrlUploadResponse response = uploadService.uploadChapterImage(
                request.getUrl(),
                request.getBookId(),
                request.getChapterId(),
                request.getName()
        );
        return ApiResponse.<FromUrlUploadResponse>builder()
                .data(response)
                .build();
    }

    // Upload ảnh cover truyện
    @PostMapping(value = "/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadCover(@RequestParam("file") MultipartFile file,
                                           @RequestParam("bookId") String bookId) {
        String url = uploadService.uploadCover(file, bookId);
        return ApiResponse.<String>builder()
                .data(url)
                .message("Cover uploaded successfully")
                .code(1000)
                .build();
    }


    // Upload avatar user
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file,
                                            @RequestParam("userId") String userId) {
        String url = uploadService.uploadAvatar(file, userId);
        return ApiResponse.<String>builder()
                .data(url)
                .message("Avatar uploaded successfully")
                .code(1000)
                .build();
    }
}

