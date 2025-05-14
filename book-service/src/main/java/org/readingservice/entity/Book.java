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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    String title;
    String author;

    String description;

    String coverUrl;

    Boolean isCompleted;
    int chapterCount;
    long viewCount;
    double averageRating;

    @ElementCollection
    List<String> categories;  // Các thể loại được truyền từ DTO

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (isCompleted == null) isCompleted = false;
        createdAt = LocalDateTime.now();
    }

    @PostPersist
    public void postPersist() {
    }
}
