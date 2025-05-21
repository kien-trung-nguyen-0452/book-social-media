package org.uploadservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileMetadata {
    @Id
    String id;
    String fileName;
    String fileType;
    List<String> fileUrl;
    @CreatedDate
    LocalDateTime uploadedAt;
    @CreatedDate
    LocalDateTime createdAt;
    String bookId;
    String relatedId;
    String category;         // "chapter-image", "avatar", "cover", "other"
    Boolean isExternalUrl;   // true nếu upload từ URL bên ngoài, false nếu upload trực tiếp
}
