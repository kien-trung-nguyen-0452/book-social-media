package org.commentservice.service;

import org.commentservice.dto.request.CommentRequest;
import org.commentservice.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse create(CommentRequest request);
    List<CommentResponse> getByBookId(String bookId);

    List<CommentResponse> getByChapterId(String chapterId);
    List<CommentResponse> getByUserId(String userId);
    void delete(String id);
    public void deleteWithoutPermission(String id);
}
