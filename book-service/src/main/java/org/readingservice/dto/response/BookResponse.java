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

    String id;
    String title;
    String subtitle;  // nếu cần, entity cũng phải có
    String description;
    String author;
    String coverUrl;
    Boolean isCompleted;

    int chapterCount;
    int viewCount;
    double averageRating;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    List<String> categories;
}
