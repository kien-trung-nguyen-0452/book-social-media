package org.readingservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "books")

public class Book {

    @Id
    String id;

    String title;
    String author;
    String description;
    String coverUrl;
    String createdBy;
    String lastUpdatedBy;
    String subtitle;


    boolean completed;
    int chapterCount;
    int viewCount;
    double averageRating;

    List<String> categories;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

}
