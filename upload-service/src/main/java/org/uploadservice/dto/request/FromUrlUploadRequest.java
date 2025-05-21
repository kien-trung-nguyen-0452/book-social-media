package org.uploadservice.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// 1. Request upload từ URL cho ảnh chương
@Data
public class FromUrlUploadRequest {
    private List<String> url;
    private String bookId;
    private String chapterId;
    private String name; // tên file hoặc tên trang
}



