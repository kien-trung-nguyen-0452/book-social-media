package org.commentservice.dto.request;



import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReplyRequest {

    @NotBlank
    private String content;

    @NotBlank
    private String parentId;

    @NotBlank
    private String username;
}
