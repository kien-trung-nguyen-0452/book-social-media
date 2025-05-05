package org.commentservice.dto.request;

import lombok.Data;

@Data
public class CommentRequest {
    private Long bookId;
    private Long chapterId;
    private String userId;
    private String content;
}
