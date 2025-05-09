package org.readingservice.repository.httpClient.dto.bookmark;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BookmarkResponse {
    private Long id;            // ID của bookmark
    private Long userId;        // Người dùng
    private Long bookId;        // Sách đã bookmark
    private Long chapterId;     // Chương đã bookmark (nếu có)
    private LocalDateTime createdAt; // Ngày tạo bookmark
}
