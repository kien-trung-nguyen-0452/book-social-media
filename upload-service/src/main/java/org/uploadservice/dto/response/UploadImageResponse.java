package org.uploadservice.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class UploadImageResponse {
    private String url;
    private String publicId;
    private String category;
    LocalDateTime uploadedAt;
}
