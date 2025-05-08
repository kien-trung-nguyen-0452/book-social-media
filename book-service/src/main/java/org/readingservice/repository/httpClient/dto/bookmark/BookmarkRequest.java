package org.readingservice.repository.httpClient.dto.bookmark;

import lombok.Data;

@Data
public class BookmarkRequest {
    private Long userId;     // Người dùng
    private Long bookId;     // Sách được bookmark
    private Long chapterId;  // Chương được bookmark (có thể null nếu chỉ bookmark sách)
}
