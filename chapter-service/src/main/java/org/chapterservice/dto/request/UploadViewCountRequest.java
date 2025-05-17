package org.chapterservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadViewCountRequest {
    String bookId;
    int viewCount;
}
