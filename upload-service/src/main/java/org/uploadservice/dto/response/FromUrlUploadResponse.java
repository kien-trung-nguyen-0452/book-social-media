package org.uploadservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FromUrlUploadResponse {
    String url;
    String publicId;
    String fileName;
    LocalDateTime createdAt;
}
