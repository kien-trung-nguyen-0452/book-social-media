package org.chapterservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterResponse {
    String id;
    String bookId;
    String chapter;
    String title;
    List<String> images;
    String updatedBy;
    String createdBy;
    String content;
    int chapterNumber;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
