package org.readingservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}