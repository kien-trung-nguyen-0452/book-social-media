package org.chapterservice.dto.request;

import lombok.Data;

import java.util.List;


@Data
public class ChapterRequest {
    private Long bookId;
    private String title;
    private String content;
    private Integer chapterNumber;
    private List<String> imageUrls;
}

