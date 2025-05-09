package org.uploadservice.dto.request;

import lombok.Data;

@Data
public class FromUrlUploadRequest {
    String url;
    String bookId;
    String chapterId;
    String name;
}
