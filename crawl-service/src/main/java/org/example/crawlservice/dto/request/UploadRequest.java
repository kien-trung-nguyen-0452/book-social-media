package org.example.crawlservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UploadRequest {
    List<String> url;
    String bookId;
    String chapterId;
    String name;
}
