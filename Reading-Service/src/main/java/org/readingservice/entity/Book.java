package org.readingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "books")
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
    Long categoryId;
    Integer chapterCount;
    Long viewCount;
    Double averageRating;

    @CreationTimestamp//tự động set thời gian
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {//Tránh lỗi tính toán
        if (viewCount == null) viewCount = 0L;
        if (averageRating == null) averageRating = 0.0;
        if (chapterCount == null) chapterCount = 0;
        if (isCompleted == null) isCompleted = false;
        createdAt = LocalDateTime.now();
    }
}
