package org.readingservice.event;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    int chapterCount;
    int viewCount;
    double averageRating;
    List<String> categories;
    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
