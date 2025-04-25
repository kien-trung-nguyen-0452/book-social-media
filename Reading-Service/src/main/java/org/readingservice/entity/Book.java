package org.readingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String subtitle;

    @Column(columnDefinition = "TEXT")
    String description;

    String author;

    String coverUrl;

    Boolean isCompleted;

    Long categoryId; // giả sử có Category Service riêng

    Integer chapterCount;

    Long viewCount;

    Double averageRating;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
