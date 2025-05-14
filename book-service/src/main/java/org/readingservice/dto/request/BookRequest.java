package org.readingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookRequest {

    @NotBlank
    String title;

    String subtitle;
    String description;
    String author;
    String coverUrl;
    int chapterCount;

    @NotNull
    Boolean isCompleted;

    @NotNull
    List<String> categories;// Gộp thành một trường categoryIds và sửa kiểu thành List<Long> (ID thể loại)

}
