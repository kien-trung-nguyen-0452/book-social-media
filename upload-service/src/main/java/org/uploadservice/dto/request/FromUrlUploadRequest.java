package org.uploadservice.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

// 1. Request upload từ URL cho ảnh chương
@Data
public class FromUrlUploadRequest {
    private String url;
    private String bookId;
    private String chapterId;
    private String name; // tên file hoặc tên trang
}



