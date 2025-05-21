package org.chapterservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChapterRequest {
    private String bookId;
    private String title;
    private List<String> images;
    private String content;
    private Integer chapterNumber;
}
