package org.readingservice.client.dto.comment;



import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private String id;                // ID của comment
    private String userId;           // ID người dùng
    private Long bookId;             // ID sách
    private Long chapterId;          // ID chương
    private String content;          // Nội dung
    private LocalDateTime createdAt; // Thời điểm tạo
}
