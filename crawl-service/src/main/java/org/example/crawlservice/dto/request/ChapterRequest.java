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
    private String bookId;
    private String title;
    private String content;//nếu dùng truyện chữ thì thêm
    private Integer chapterNumber;
    private List<String> images;
}
