package org.commentservice.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String id;
    String bookId;
    String UserId;
    String chapterId;

    String username;
    String content;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    private List<CommentResponse> replies;
}
