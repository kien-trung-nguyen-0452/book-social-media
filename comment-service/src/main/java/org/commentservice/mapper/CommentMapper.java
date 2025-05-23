package org.commentservice.mapper;

import org.commentservice.dto.request.CommentRootRequest;
import org.commentservice.dto.request.CommentReplyRequest;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntity(CommentRootRequest request);

    Comment toEntity(CommentReplyRequest request);

    CommentResponse toResponse(Comment comment);
}
