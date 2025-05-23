package org.example.searchservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BookSearchingResult {
    String id;
    String coverUrl;
    String title;
    int chapterCount;
    private String author;
    private List<String> categories;
    LocalDate updatedAt;
}
