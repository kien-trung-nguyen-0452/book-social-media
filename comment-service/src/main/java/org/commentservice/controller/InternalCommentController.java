package org.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.commentservice.dto.common.ApiResponse;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalCommentController {

    private final CommentService commentService;

    @GetMapping("/{username}")
    public ApiResponse<List<CommentResponse>> getByUsername(@PathVariable String username) {
        return ApiResponse.<List<CommentResponse>>builder()
                .code(1000)
                .result(commentService.getByUsername(username))
                .message("Comments for user")
                .build();
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<CommentResponse>> getByBookId(@PathVariable String bookId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .code(1000)
                .result(commentService.getByBookId(bookId))
                .message("Comments for book")
                .build();
    }

    @GetMapping("/chapter/{chapterId}")
    public ApiResponse<List<CommentResponse>> getByChapterId(@PathVariable String chapterId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .code(1000)
                .result(commentService.getByChapterId(chapterId))
                .message("Comments for chapter")
                .build();
    }
}
