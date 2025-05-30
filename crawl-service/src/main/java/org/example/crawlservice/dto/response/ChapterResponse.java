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
    String id;
    String bookId;
    String title;
    String content;
    Integer chapterNumber;
    List<String> images;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
