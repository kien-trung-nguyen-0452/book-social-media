package org.uploadservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.uploadservice.dto.common.ApiResponse;
import org.uploadservice.dto.request.FromUrlUploadRequest;
import org.uploadservice.dto.response.*;
import org.uploadservice.service.UploadService;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    // Upload ảnh bình thường MultipartFile
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UploadImageResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        UploadImageResponse response = uploadService.uploadImage(file);
        return ApiResponse.<UploadImageResponse>builder()
                .data(response)
                .message("Image uploaded successfully")
                .code(1000)
                .build();
    }

    // Upload ảnh chương từ URL
    @PostMapping("/from-url")
    public ApiResponse<FromUrlUploadResponse> uploadFromUrl(@RequestBody FromUrlUploadRequest request) {
        FromUrlUploadResponse response = uploadService.uploadChapterImage(request);
        return ApiResponse.<FromUrlUploadResponse>builder()
                .data(response)
                .message("Image uploaded from URL successfully")
                .code(1000)
                .build();
    }


    // Upload ảnh bìa sách
    @PostMapping(value = "/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CoverUploadResponse> uploadCover(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("bookId") String bookId) {
        CoverUploadResponse response = uploadService.uploadCover(file, bookId);
        return ApiResponse.<CoverUploadResponse>builder()
                .data(response)
                .message("Cover uploaded successfully")
                .code(1000)
                .build();
    }

    // Upload avatar người dùng
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<AvatarUploadResponse> uploadAvatar(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("userId") String userId) {
        AvatarUploadResponse response = uploadService.uploadAvatar(file, userId);
        return ApiResponse.<AvatarUploadResponse>builder()
                .data(response)
                .message("Avatar uploaded successfully")
                .code(1000)
                .build();
    }
    @DeleteMapping("/book/{bookId}")
    public ApiResponse<Void> deleteFilesByBookId(@PathVariable String bookId) {
        uploadService.deleteByBookId(bookId);
        return ApiResponse.<Void>builder()
                .data(null)
                .message("File metadata deleted successfully")
                .code(1000)
                .build();
    }

}
