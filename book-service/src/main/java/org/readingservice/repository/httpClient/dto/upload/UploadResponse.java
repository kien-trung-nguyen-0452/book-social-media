package org.readingservice.client.dto.upload;

import lombok.Data;

@Data
public class UploadResponse {
    private String id;
    private String imageUrl;
    private Long chapterId;
}
