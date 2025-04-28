package org.readingservice.client;

import lombok.Data;

@Data
public class ChapterResponse {
    private Long id;
    private String title;
    private Integer order;
    private Long bookId;
}
