package org.readingservice.event;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookEvent {
    String id;
    String title;
    String author;
    String description;
    String coverUrl;
    boolean isCompleted;
    int chapterCount;
    long viewCount;
    double averageRating;
    List<String> categories;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
