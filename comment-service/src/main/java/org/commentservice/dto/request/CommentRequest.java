package org.commentservice.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CommentRequest {
    String bookId;
    String chapterId;
    String userId;
    String username;
    String content;

}
