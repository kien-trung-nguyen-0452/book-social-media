package org.commentservice.repository;

import org.commentservice.dto.response.CommentResponse;
import org.commentservice.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
    List<Comment> findByChapterId(String chapterId);
    List<Comment> findByUserId(String userId);
    void deleteByBookId(String bookId);
    void deleteByChapterId(String chapterId);
    void deleteByUserId(String userId);

}
