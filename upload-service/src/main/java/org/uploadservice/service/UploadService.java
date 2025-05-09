package org.uploadservice.service;

import org.springframework.web.multipart.MultipartFile;
import org.uploadservice.dto.response.FromUrlUploadResponse;

public interface UploadService {
    String uploadImage(MultipartFile file);
    FromUrlUploadResponse uploadChapterImage (String url, String bookId, String chapterId, String name);
}
