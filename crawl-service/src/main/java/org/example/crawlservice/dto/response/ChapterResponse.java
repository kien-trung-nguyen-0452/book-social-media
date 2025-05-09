package org.example.crawlservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterResponse {
    Long id;
    Long bookId;
    String title;
    String content;
    Integer chapterNumber;
    List<String> imageUrls;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
