package org.readingservice.repository.httpClient.dto.comment;

import lombok.Data;

@Data
public class CommentRequest {
    private String userId;       // ID người dùng viết comment
    private Long bookId;         // ID sách (nếu cần)
    private Long chapterId;      // ID chương mà comment được gắn vào
    private String content;      // Nội dung bình luận
}
