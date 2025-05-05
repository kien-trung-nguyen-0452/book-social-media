package org.analyticsservice.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReadingLogResponse {
    private String id; // MongoDB ID là String
    private Long userId;
    private Long bookId;
    private Long chapterId;
    private Long duration;
    private LocalDateTime timestamp;
}
