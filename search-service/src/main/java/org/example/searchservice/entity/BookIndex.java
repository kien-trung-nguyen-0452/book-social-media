package org.example.searchservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(indexName = "BookIndex")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookIndex {
    @Id
    String id;
    String title;
    String subtitle;
    String description;
    String author;
    String coverUrl;
    Boolean isCompleted;
    int chapterCount;
    LocalDate createdAt;
    LocalDate updatedAt;

    List<String> categories;


}
