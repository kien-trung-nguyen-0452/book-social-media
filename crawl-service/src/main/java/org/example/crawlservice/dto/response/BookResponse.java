package org.example.crawlservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    String bookId;
    String title;
    String subtitle;
    String description;
    String author;
    String coverUrl;
    String createdBy;
    List<String> categories;
    String lastUpdatedBy;
    int chapterCount;
    int viewCount;
    double averageRating;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}