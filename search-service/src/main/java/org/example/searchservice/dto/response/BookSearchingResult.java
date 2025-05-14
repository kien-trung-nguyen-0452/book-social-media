package org.example.searchservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookSearchingResult {
    String id;
    String coverUrl;
    String title;
    int chapterCount;
    LocalDate updatedAt;

}
