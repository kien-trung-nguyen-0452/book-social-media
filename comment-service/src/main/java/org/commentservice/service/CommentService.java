package org.commentservice.service;

import org.commentservice.dto.request.CommentReplyRequest;
import org.commentservice.dto.request.CommentRootRequest;
import org.commentservice.dto.response.CommentResponse;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface CommentService {
    CommentResponse createRoot(CommentRootRequest request);
    CommentResponse createReply(CommentReplyRequest request);
    List<CommentResponse> getByUsername(String username);

    List<CommentResponse> getByBookId(String bookId);
    List<CommentResponse> getByChapterId(String chapterId);
    void deleteByUsername(@P("username") String username);
    List<CommentResponse> getReplies(String parentId);
    CommentResponse update(String id, CommentReplyRequest request);
    void deleteByUserIdWithoutAuth(String userId);
    void delete(String id, @P("username") String username);

}
