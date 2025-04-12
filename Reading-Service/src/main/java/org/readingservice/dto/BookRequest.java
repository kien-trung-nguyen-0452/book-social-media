package org.readingservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
