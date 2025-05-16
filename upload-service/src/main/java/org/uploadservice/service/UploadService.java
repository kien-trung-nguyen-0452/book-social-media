package org.uploadservice.service;

import org.springframework.web.multipart.MultipartFile;
import org.uploadservice.dto.request.FromUrlUploadRequest;
import org.uploadservice.dto.response.*;

public interface UploadService {

    UploadResponse uploadImage(MultipartFile file);

    FromUrlUploadResponse uploadChapterImage(FromUrlUploadRequest request);

    CoverUploadResponse uploadCover(MultipartFile file, String bookId);

    AvatarUploadResponse uploadAvatar(MultipartFile file, String userId);


}
