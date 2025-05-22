package org.uploadservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.uploadservice.dto.request.FromUrlUploadRequest;
import org.uploadservice.dto.response.*;
import org.uploadservice.entity.FileMetadata;
import org.uploadservice.exception.ErrorCode;
import org.uploadservice.exception.ServiceException;
import org.uploadservice.mapper.FileMetadataMapper;
import org.uploadservice.repository.FileMetadataRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final Cloudinary cloudinary;
    private final FileMetadataMapper mapper;
    private final FileMetadataRepository repository;

    @Override
    public UploadImageResponse uploadImage(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            FileMetadata metadata = FileMetadata.builder()
                    .id((String) uploadResult.get("public_id"))
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileUrl((List<String>) uploadResult.get("secure_url"))
                    .uploadedAt(LocalDateTime.now())
                    .category("other")
                    .isExternalUrl(false)
                    .build();

            FileMetadata saved = repository.save(metadata);
            return mapper.toUploadResponse(saved);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public FromUrlUploadResponse uploadChapterImage(FromUrlUploadRequest request) {
        try {
            List<String> uploadedUrls = new ArrayList<>();

            for (int i = 0; i < request.getUrl().size(); i++) {
                String imageUrl = request.getUrl().get(i);

                String path = generateChapterPath(
                        request.getBookId(),
                        request.getChapterId(),
                        request.getName() + "_page" + (i + 1) // unique name
                );

                Map<String, Object> options = ObjectUtils.asMap(
                        "folder", "manga/",
                        "public_id", path,
                        "overwrite", true,
                        "context", String.format("book=%s|chapter=%s|page=%d",
                                request.getBookId(), request.getChapterId(), i + 1)
                );

                Map<?, ?> uploadResult = cloudinary.uploader().upload(imageUrl, options);
                uploadedUrls.add((String) uploadResult.get("url"));
            }

            FileMetadata metadata = FileMetadata.builder()
                    .id(request.getChapterId())
                    .fileName(request.getName())
                    .fileUrl(uploadedUrls)
                    .fileType("URL PICTURE") // hoặc lấy từ uploadResult
                    .uploadedAt(LocalDateTime.now())
                    .bookId(request.getBookId())
                    .relatedId(request.getChapterId())
                    .category("chapter-image")
                    .isExternalUrl(true)
                    .build();

            repository.save(metadata);

            return FromUrlUploadResponse.builder()
                    .url(uploadedUrls)
                    .build();
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CoverUploadResponse uploadCover(MultipartFile file, String bookId) {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", "covers/",
                    "public_id", "book_" + bookId,
                    "overwrite", true,
                    "resource_type", "image"
            );
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

            FileMetadata metadata = FileMetadata.builder()
                    .id((String) uploadResult.get("public_id"))
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileUrl((List<String>) uploadResult.get("secure_url"))
                    .uploadedAt(LocalDateTime.now())
                    .bookId(bookId)
                    .category("cover")
                    .isExternalUrl(false)
                    .build();

            FileMetadata saved = repository.save(metadata);
            return mapper.toCoverUploadResponse(saved);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    public AvatarUploadResponse uploadAvatar(MultipartFile file, String userId) {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", "avatars/",
                    "public_id", "user_" + userId,
                    "overwrite", true,
                    "resource_type", "image"
            );
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

            FileMetadata metadata = FileMetadata.builder()
                    .id((String) uploadResult.get("public_id"))
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileUrl((List<String>) uploadResult.get("secure_url"))
                    .uploadedAt(LocalDateTime.now())
                    .relatedId(userId)
                    .category("avatar")
                    .isExternalUrl(false)
                    .build();

            FileMetadata saved = repository.save(metadata);
            return mapper.toAvatarUploadResponse(saved);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    public void deleteByBookId(String bookId) {
        repository.deleteByBookId(bookId);
    }
    private String generateChapterPath(String bookId, String chapterId, String name) {
        return String.format("%s/%s/%s", bookId, chapterId, name);
    }
}
