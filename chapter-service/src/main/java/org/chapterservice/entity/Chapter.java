package org.chapterservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_book_id", columnList = "bookId"),
        @Index(name = "idx_book_chapter", columnList = "bookId, chapterNumber")
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long bookId;

    String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String content;

    Integer chapterNumber;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
    @ElementCollection
     List<String> imageUrls; // Thêm dòng này
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
