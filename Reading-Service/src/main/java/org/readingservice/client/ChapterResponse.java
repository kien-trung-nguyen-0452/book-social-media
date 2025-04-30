package org.readingservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChapterResponse {
    private Long id;
    private String title;
    private Integer order;
    private Long bookId;
}
