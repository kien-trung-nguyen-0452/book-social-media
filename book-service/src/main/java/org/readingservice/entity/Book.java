package org.readingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    String title;
    String author;

    @Column(columnDefinition = "TEXT")
    String description;

    String coverUrl;

    Boolean isCompleted;
    Integer chapterCount;
    Long viewCount;
    Double averageRating;

    @ElementCollection
    List<String> categories;  // Các thể loại được truyền từ DTO

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (viewCount == null) viewCount = 0L;
        if (averageRating == null) averageRating = 0.0;
        if (chapterCount == null) chapterCount = 0;
        if (isCompleted == null) isCompleted = false;
        createdAt = LocalDateTime.now();
    }

    @PostPersist
    public void postPersist() {
    }
}
