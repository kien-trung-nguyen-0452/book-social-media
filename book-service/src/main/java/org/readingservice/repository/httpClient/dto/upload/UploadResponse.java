package org.readingservice.repository.httpClient.dto.upload;

import lombok.Data;

@Data
public class UploadResponse {
    private String id;
    private String imageUrl;
    private Long chapterId;
}
