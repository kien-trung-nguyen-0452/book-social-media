package org.readingservice.repository.httpClient.dto.chapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ChapterResponse {
    private Long id;
    private Long bookId;
    private String title;
    private String content;
    private Integer chapterNumber;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

