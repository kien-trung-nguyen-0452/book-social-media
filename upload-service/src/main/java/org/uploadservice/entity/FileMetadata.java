package org.uploadservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileMetadata {
    @Id
    String id;               // publicId trên Cloudinary hoặc ID lưu Mongo
    String fileName;         // tên file
    String fileType;         // loại file (image/png, ...)
    String fileUrl;          // URL truy cập file
    LocalDateTime uploadedAt;
    String bookId;           // nếu là file liên quan sách
    String relatedId;        // có thể là chapterId, userId, ...
    String category;         // "chapter-image", "avatar", "cover", "other"
    Boolean isExternalUrl;   // true nếu upload từ URL bên ngoài, false nếu upload trực tiếp
}
