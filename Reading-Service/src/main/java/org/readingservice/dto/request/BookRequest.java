package org.readingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @NonNull
    Boolean isCompleted;

    @NonNull
    Long categoryId;

}
