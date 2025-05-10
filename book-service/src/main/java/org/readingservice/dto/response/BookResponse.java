package org.readingservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    Long id;
    String title;
    String subtitle;
    String description;
    String author;
    String coverUrl;
    Boolean isCompleted;
    Long categoryId;
    Integer chapterCount;
    Long viewCount;
    Double averageRating;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    List<String> categories;
}
