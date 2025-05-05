package org.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.commentservice.dto.common.ApiResponse;
import org.commentservice.dto.request.CommentRequest;
import org.commentservice.dto.response.CommentResponse;
import org.commentservice.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> create(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(commentService.create(request), "Comment created", 200));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(new ApiResponse<>(commentService.getByBookId(bookId), "Comments for book", 200));
    }

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getByChapterId(@PathVariable Long chapterId) {
        return ResponseEntity.ok(new ApiResponse<>(commentService.getByChapterId(chapterId), "Comments for chapter", 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        commentService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "Comment deleted", 200));
    }
}
