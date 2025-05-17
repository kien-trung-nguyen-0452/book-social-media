package org.example.crawlservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    boolean isCompleted;
    String createdBy;
    String lastUpdatedBy;
    int chapterCount;
    int viewCount;
    double averageRating;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    LocalDateTime updatedAt;

}