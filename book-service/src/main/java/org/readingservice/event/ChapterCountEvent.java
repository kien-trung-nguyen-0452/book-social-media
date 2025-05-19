package org.readingservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChapterCountEvent {
    private String bookId;
    private int count;
}
