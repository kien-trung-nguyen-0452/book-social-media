package org.bookmarkservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookmarkResponse {
    private String id;
    private String userId;
    private String bookId;
    private Integer chapterNumber;
    private LocalDateTime createdAt;
}