package org.readingservice.client;

import org.readingservice.client.dto.comment.CommentRequest;
import org.readingservice.client.dto.comment.CommentResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "comment-service", url = "${service.comment.base-url}")
public interface CommentServiceClient {

    @GetMapping("/api/comments/chapter/{chapterId}")
    ApiResponse<List<CommentResponse>> getCommentsByChapterId(@PathVariable("chapterId") Long chapterId);

    @PostMapping("/api/comments")
    ApiResponse<CommentResponse> addComment(@RequestBody CommentRequest request);

    @DeleteMapping("/api/comments/{commentId}")
    ApiResponse<Void> deleteComment(@PathVariable("commentId") String commentId);
}

