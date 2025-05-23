package org.analyticsservice.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReadingLogResponse {
    private String id;
    private String userId;
    private String bookId;
    private String chapterId;
    private String duration;
    private LocalDateTime timestamp;
}
