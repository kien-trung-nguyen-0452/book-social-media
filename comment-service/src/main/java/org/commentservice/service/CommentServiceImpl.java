package org.commentservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.commentservice.dto.request.CommentRootRequest;
import org.commentservice.dto.request.CommentReplyRequest;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.entity.Comment;
import org.commentservice.exception.ErrorCode;
import org.commentservice.exception.ServiceException;
import org.commentservice.mapper.CommentMapper;
import org.commentservice.repository.CommentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    CommentRepository repository;
    CommentMapper mapper;

    @Override
    @PreAuthorize("#request.username == authentication.name or hasRole('ADMIN')")
    public CommentResponse createRoot(@P("request") CommentRootRequest request) {
        Comment comment = mapper.toEntity(request);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        CommentResponse response = mapper.toResponse(repository.save(comment));
        log.info("Created root comment: {}", response);
        return response;
    }

    @Override
    @PreAuthorize("#request.username == authentication.name or hasRole('ADMIN')")
    public CommentResponse createReply(@P("request") CommentReplyRequest request) {
        Comment comment = mapper.toEntity(request);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        CommentResponse response = mapper.toResponse(repository.save(comment));
        log.info("Created reply: {}", response);
        return response;
    }

    @Override
    @PreAuthorize("#request.username == authentication.name or hasRole('ADMIN')")
    public CommentResponse update(String id, @P("request") CommentReplyRequest request) {
        Comment existingComment = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Comment with ID {} not found for update", id);
                    return new ServiceException(ErrorCode.COMMENT_NOT_FOUND);
                });
        existingComment.setContent(request.getContent());
        existingComment.setUpdatedAt(LocalDateTime.now());

        CommentResponse response = mapper.toResponse(repository.save(existingComment));
        log.info("Updated comment: {}", response);
        return response;
    }

    @Override
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public void deleteByUsername(@P("username") String username) {
        List<Comment> comments = repository.findByUsername(username);
        for (Comment comment : comments) {
            deleteRepliesRecursive(comment.getId());
            repository.deleteById(comment.getId());
        }
        log.info("Deleted all comments for user {}", username);
    }

    @Override
    public List<CommentResponse> getByUsername(String username) {
        return repository.findByUsername(username)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void deleteByUsernameWithoutAuth(String username) {
        List<Comment> comments = repository.findByUsername(username);
        for (Comment comment : comments) {
            deleteRepliesRecursive(comment.getId());
            repository.deleteById(comment.getId());
        }
        log.info("Deleted all comments for user {}", username);
    }

    @Override
    public List<CommentResponse> getReplies(String parentId) {
        return repository.findByParentId(parentId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<CommentResponse> getByBookId(String bookId) {
        return repository.findByBookId(bookId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<CommentResponse> getByChapterId(String chapterId) {
        return repository.findByChapterId(chapterId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public void delete(String id, @P("username") String username) {
        if (!repository.existsById(id)) {
            throw new ServiceException(ErrorCode.COMMENT_NOT_FOUND);
        }
        deleteRepliesRecursive(id);
        repository.deleteById(id);
        log.info("Deleted comment with id {}", id);
    }

    private void deleteRepliesRecursive(String parentId) {
        List<Comment> replies = repository.findByParentId(parentId);
        for (Comment reply : replies) {
            deleteRepliesRecursive(reply.getId());
            repository.deleteById(reply.getId());
        }
    }
}
