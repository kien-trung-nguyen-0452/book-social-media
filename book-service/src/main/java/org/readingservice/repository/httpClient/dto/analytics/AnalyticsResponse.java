package org.readingservice.repository.httpClient.dto.analytics;

import lombok.Data;

@Data
public class AnalyticsResponse {
    private Long bookId;       // ID sách
    private Long totalDuration; // Tổng thời lượng đọc
    private int readCount;      // Số lần đọc
}
