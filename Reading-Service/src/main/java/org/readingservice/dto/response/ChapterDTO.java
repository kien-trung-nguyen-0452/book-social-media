package org.readingservice.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDTO {
    private Long id;
    private String title;
    private String content;
    private Long bookId;
}