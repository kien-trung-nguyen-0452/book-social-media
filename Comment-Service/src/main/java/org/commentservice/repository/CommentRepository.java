package org.commentservice.repository;

import org.commentservice.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(Long bookId);
    List<Comment> findByChapterId(Long chapterId);
}
