package org.readingservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {

    String  id;
    String title;
    String subtitle;
    String description;
    String author;
    String coverUrl;
    Boolean isCompleted;


    Integer chapterCount;
    String viewCount;
    Double averageRating;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    // Nếu bạn muốn giữ danh sách thể loại (category) dưới dạng chuỗi, thì giữ nguyên List<String>
    List<String> categories;  // Các thể loại có thể là tên (String) hoặc mô tả
}
