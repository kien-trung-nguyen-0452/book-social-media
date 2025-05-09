package org.example.crawlservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}