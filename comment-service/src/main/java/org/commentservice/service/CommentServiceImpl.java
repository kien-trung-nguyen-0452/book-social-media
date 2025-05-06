package org.commentservice.service;

import lombok.RequiredArgsConstructor;
import org.commentservice.dto.request.CommentRequest;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.entity.Comment;
import org.commentservice.exception.ErrorCode;
import org.commentservice.exception.ServiceException;
import org.commentservice.mapper.CommentMapper;
import org.commentservice.repository.CommentRepository;
import org.commentservice.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Override
    public CommentResponse create(CommentRequest request) {
        Comment comment = mapper.toEntity(request);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return mapper.toResponse(repository.save(comment));
    }

    @Override
    public List<CommentResponse> getByBookId(Long bookId) {
        return repository.findByBookId(bookId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<CommentResponse> getByChapterId(Long chapterId) {
        return repository.findByChapterId(chapterId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ServiceException(ErrorCode.COMMENT_NOT_FOUND, "Comment not found");
        }
        repository.deleteById(id);
    }
}
