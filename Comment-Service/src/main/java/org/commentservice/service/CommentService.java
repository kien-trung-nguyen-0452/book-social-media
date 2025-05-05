package org.commentservice.service;

import org.commentservice.dto.request.CommentRequest;
import org.commentservice.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse create(CommentRequest request);
    List<CommentResponse> getByBookId(Long bookId);
    List<CommentResponse> getByChapterId(Long chapterId);
    void delete(String id);
}
