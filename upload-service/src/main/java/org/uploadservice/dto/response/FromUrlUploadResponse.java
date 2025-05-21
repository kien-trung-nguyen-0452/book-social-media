package org.uploadservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FromUrlUploadResponse {
    List<String> url;
    String publicId;
    String fileName;
    LocalDateTime createdAt;
}
