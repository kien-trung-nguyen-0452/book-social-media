package org.readingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


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
    String createdBy;
    String UpdatedBy;


    @NotNull
    List<String> categories;

}
