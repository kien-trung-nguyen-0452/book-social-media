package org.readingservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookEvent {
    String id;
    String title;
    String author;
    String description;
    String coverUrl;
    boolean isCompleted;
    int chapterCount;
    int viewCount;
    double averageRating;
    List<String> categories;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
