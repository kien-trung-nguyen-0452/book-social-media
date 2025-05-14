package org.example.searchservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookIndexUpdateRequest{
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
