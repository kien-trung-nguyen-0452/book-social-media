package org.analyticsservice.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "reading_logs") // tên collection trong MongoDB
public class ReadingLog {

    @Id
    private String id; // MongoDB dùng String cho _id

    private Long userId;
    private Long bookId;
    private Long chapterId;
    private Long duration;
    private LocalDateTime timestamp;
}


