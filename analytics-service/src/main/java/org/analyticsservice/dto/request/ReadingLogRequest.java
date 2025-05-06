package org.analyticsservice.dto.request;


import lombok.Data;

@Data
public class ReadingLogRequest {
    private Long userId;
    private Long bookId;
    private Long chapterId;
    private Long duration;
}
