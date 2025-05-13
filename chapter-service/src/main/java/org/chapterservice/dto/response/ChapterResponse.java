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
    private String id;
    private String chapter;
    private String title;
    private List<String> images;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
