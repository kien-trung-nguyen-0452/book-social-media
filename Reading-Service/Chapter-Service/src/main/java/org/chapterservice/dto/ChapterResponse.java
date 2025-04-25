package org.chapterservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChapterResponse {
    Long id;
    Long bookId;
    String title;
    String content;
    Integer chapterNumber;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
