package org.chapterservice.dto;

import lombok.Data;

@Data
public class ChapterRequest {
    Long bookId;
    String title;
    String content;
    Integer chapterNumber;
}
