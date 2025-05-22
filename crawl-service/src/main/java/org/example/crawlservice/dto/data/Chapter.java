package org.example.crawlservice.dto.data;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chapter {
     String id;
     String chapterNumber;
     String title;
     List<String> images;
}
