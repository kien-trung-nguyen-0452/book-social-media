package org.chapterservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChapterRequest {
    private String bookId;
    private String chapter;
    private String title;
    private String updatedBy;
    private List<String> images;  // List of image URLs (local paths in your case)
    private String content;       // Thêm trường content nếu cần
    private Integer chapterNumber; // Thêm trường chapterNumber nếu cần
}
