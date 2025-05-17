package org.example.crawlservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookRequest{
    String title;
    String description;
    String author;
    List<String> categoryId;
    String coverUrl;

}

