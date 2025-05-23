package org.analyticsservice.dto.request;


import lombok.Data;

@Data
public class ReadingLogRequest {
    private String userId;
    private String bookId;
    private String chapterId;
    private String duration;
}
