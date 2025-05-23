package org.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.commentservice.dto.common.ApiResponse;
import org.commentservice.dto.request.CommentRootRequest;
import org.commentservice.dto.request.CommentReplyRequest;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class ExternalCommentController {

    private final CommentService commentService;

    @PostMapping("/root")
    public ApiResponse<CommentResponse> createRoot(@RequestBody CommentRootRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .code(1000)
                .result(commentService.createRoot(request))
                .build();
    }

    @PostMapping("/reply")
    public ApiResponse<CommentResponse> createReply(@RequestBody CommentReplyRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .code(1000)
                .result(commentService.createReply(request))
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<CommentResponse> update(@PathVariable String id,
                                               @RequestBody CommentReplyRequest request,
                                               @AuthenticationPrincipal Principal principal) {
        CommentResponse updated = commentService.update(id, request);
        return ApiResponse.<CommentResponse>builder()
                .code(1000)
                .result(updated)
                .build();
    }

    @GetMapping("/{id}/replies")
    public ApiResponse<List<CommentResponse>> getReplies(@PathVariable String id) {
        return ApiResponse.<List<CommentResponse>>builder()
                .code(1000)
                .result(commentService.getReplies(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id, @AuthenticationPrincipal Principal principal) {
        commentService.delete(id, principal.getName());
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Comment deleted")
                .build();
    }
}
