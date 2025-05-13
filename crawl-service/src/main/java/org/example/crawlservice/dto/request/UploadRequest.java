package org.example.crawlservice.dto.request;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class UploadRequest {
    String url;
    String bookId;
    String chapterId;
    String name;
}
