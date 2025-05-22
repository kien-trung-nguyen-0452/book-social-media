package org.commentservice.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String id;
    String bookId;
    String chapterId;
    String userId;
    String username;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
