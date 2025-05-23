package org.commentservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.commentservice.dto.request.CommentRequest;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.entity.Comment;
import org.commentservice.exception.ErrorCode;
import org.commentservice.exception.ServiceException;
import org.commentservice.mapper.CommentMapper;
import org.commentservice.repository.CommentRepository;
import org.commentservice.repository.httpClient.AuthServiceClient;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final CommentMapper mapper;

    private final AuthServiceClient authServiceClient;

    @Override
    @PreAuthorize("request.username.equals(authentication.name) or hasRole('ADMIN')")
    public CommentResponse create(@P("request")CommentRequest request) {
        var id = authServiceClient.getUserId().getResult();

        if(id.equals(request.getUserId())){

        Comment comment = mapper.toEntity(request);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return mapper.toResponse(repository.save(comment));}
        else {
            throw new ServiceException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Override
    public List<CommentResponse> getByBookId(String bookId) {
        return repository.findByBookId(bookId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    @Override
    public List<CommentResponse> getByUserId(String userId){
        return repository.findByUserId(userId)
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
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ServiceException(ErrorCode.COMMENT_NOT_FOUND);
        }
        repository.deleteById(id);
    }
    // Dùng cho HTTP có phân quyền
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public void deleteWithPermission(String id) {
        deleteInternal(id);
    }

    // Dùng cho Kafka - không phân quyền
    public void deleteWithoutPermission(String id) {
        deleteInternal(id);
    }

    // Logic chính
    private void deleteInternal(String id) {
        if (!repository.existsById(id)) {
            throw new ServiceException(ErrorCode.COMMENT_NOT_FOUND);
        }
        repository.deleteById(id);
    }

}
