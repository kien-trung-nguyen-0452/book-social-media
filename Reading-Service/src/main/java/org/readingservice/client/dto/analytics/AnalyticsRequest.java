package org.readingservice.client.dto.analytics;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AnalyticsRequest {
    private Long userId;        // Người dùng đọc
    private Long bookId;        // Sách đang đọc
    private Long chapterId;     // Chương đang đọc
    private LocalDateTime timestamp; // Thời điểm bắt đầu đọc
    private int duration;       // Thời lượng đọc (giây)
}
