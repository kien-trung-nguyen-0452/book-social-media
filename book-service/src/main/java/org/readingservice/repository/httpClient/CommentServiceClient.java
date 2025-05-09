package org.readingservice.repository.httpClient;

import org.readingservice.repository.httpClient.dto.comment.CommentRequest;
import org.readingservice.repository.httpClient.dto.comment.CommentResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "comment-service")
public interface CommentServiceClient {

    @GetMapping("/comments/chapter/{chapterId}")
    ApiResponse<List<CommentResponse>> getCommentsByChapterId(@PathVariable("chapterId") Long chapterId);

    @PostMapping("/comments")
    ApiResponse<CommentResponse> addComment(@RequestBody CommentRequest request);

    @DeleteMapping("/comments/{commentId}")
    ApiResponse<Void> deleteComment(@PathVariable("commentId") String commentId);
}

