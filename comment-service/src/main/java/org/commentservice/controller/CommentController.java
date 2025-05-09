package org.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.commentservice.dto.common.ApiResponse;
import org.commentservice.dto.request.CommentRequest;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentResponse> create(@RequestBody CommentRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .data(commentService.create(request))
                .message("Comment created")
                .build();
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<CommentResponse>> getByBookId(@PathVariable Long bookId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .data(commentService.getByBookId(bookId))
                .message("Comments for book")
                .build();
    }

    @GetMapping("/chapter/{chapterId}")
    public ApiResponse<List<CommentResponse>> getByChapterId(@PathVariable Long chapterId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .data(commentService.getByChapterId(chapterId))
                .message("Comments for chapter")
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
