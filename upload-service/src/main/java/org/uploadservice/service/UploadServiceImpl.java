package org.uploadservice.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.uploadservice.service.UploadService;

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
}
