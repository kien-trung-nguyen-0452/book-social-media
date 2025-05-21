package org.readingservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreationResponse {
    String id;
    String title;
    String author;
    String description;
    String coverUrl;
    String createdBy;
    String UpdatedBy;
    String subtitle;
    Boolean isCompleted;
    int chapterCount;
    int viewCount;

    List<String> categories;


    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
