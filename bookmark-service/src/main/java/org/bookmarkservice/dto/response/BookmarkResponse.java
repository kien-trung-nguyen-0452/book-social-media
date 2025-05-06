package org.bookmarkservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookmarkResponse {
    private String id;
    private Long userId;
    private Long bookId;
    private Integer chapterNumber;
    private LocalDateTime createdAt;
}