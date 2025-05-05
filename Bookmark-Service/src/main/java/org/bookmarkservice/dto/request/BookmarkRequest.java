package org.bookmarkservice.dto.request;

import lombok.Data;

@Data
public class BookmarkRequest {
    private Long userId;
    private Long bookId;
    private Integer chapterNumber;
}
