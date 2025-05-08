package org.chapterservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Integer chapterNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
