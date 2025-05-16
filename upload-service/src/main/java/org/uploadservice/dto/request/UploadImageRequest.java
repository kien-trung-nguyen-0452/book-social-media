package org.uploadservice.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data

public class UploadImageRequest {
    private MultipartFile file;
}