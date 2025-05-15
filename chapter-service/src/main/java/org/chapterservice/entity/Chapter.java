package org.chapterservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "chapters") // Dùng @Document thay cho @Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chapter {

    @Id
    String id; // MongoDB sử dụng String id theo ObjectId, không cần @GeneratedValue

    String bookId;
    String title;
    String content; // Không cần @Lob hoặc @Column
    String chapter;
    String updatedBy;

    int chapterNumber;
    List<String> imageUrls;

    // Tự động gán thời gian tạo/cập nhật
    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();
}
