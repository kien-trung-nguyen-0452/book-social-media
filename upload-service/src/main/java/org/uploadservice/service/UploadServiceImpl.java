package org.uploadservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.uploadservice.dto.response.FromUrlUploadResponse;
import org.uploadservice.exception.ErrorCode;
import org.uploadservice.exception.ServiceException;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public FromUrlUploadResponse uploadChapterImage(String url, String bookId, String chapterId, String name) {
        String baseFolder = "manga/";
        String path = generateChapterPath(bookId,chapterId, name);
        path = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
        try {
            Map<?, ?> options = ObjectUtils.asMap(
                    "folder", baseFolder,
                    "public_id", path,
                    "overwrite", true,
                    "context", String.format("book=%s|chapter=%s|page=%s", bookId, chapterId, name)
            );

            Map<?, ?> uploadResult = cloudinary.uploader().upload(url, options);
            return FromUrlUploadResponse.builder()
                    .url((String) uploadResult.get("url"))
                    .public_id((String) uploadResult.get("public_id"))
                    .build();

        } catch (IOException e) {
            throw new ServiceException(ErrorCode.FILE_UPLOAD_FAILED);
        }

    }

    private String generateChapterPath(String bookId, String chapterId, String name){
       return String.format("/%s/%s/%s", bookId, chapterId, name);
    }


}
