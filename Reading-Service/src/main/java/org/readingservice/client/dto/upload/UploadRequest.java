package org.readingservice.client.dto.upload;

import lombok.Data;

@Data

public class UploadRequest {
    private Long chapterId;
    private String imageDataBase64; // hoặc Multipart, tuỳ backend
}

