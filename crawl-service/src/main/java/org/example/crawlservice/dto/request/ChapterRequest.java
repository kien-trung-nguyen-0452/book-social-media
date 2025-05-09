package org.example.crawlservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ChapterRequest{

    private Long bookId;
    private String title;
    private String content;
    private Integer chapterNumber;
    private List<String> imageUrls;
}
