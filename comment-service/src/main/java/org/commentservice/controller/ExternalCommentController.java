package org.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.commentservice.dto.common.ApiResponse;
import org.commentservice.dto.request.CommentRequest;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class ExternalCommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentResponse> create(@RequestBody CommentRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.create(request))
                .message("Comment created")
                .build();
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        commentService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Comment deleted")
                .build();
    }
}
