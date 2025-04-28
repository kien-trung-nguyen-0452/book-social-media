package org.readingservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookRequest {

    String title;

    String subtitle;

    String description;

    String author;

    String coverUrl;

    Boolean isCompleted;

    Long categoryId;
}
