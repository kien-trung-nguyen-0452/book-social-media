package org.chapterservice.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
