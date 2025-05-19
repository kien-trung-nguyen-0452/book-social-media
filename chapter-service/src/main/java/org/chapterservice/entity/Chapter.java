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
    String id;

    String bookId;
    String title;
    String content;
    String chapter;
    String updatedBy;
    String createdBy;
    int chapterNumber;
    List<String> images;
    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();
}
