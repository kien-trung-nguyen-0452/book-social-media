package org.chapterservice.controller;

import lombok.RequiredArgsConstructor;
import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.respone.ChapterResponse;
import org.chapterservice.service.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping
    public ResponseEntity<ChapterResponse> createChapter(@RequestBody ChapterRequest request) {
        return ResponseEntity.ok(chapterService.createChapter(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterResponse> getChapterById(@PathVariable Long id) {
        return ResponseEntity.ok(chapterService.getChapterById(id));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ChapterResponse>> getChaptersByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(chapterService.getChaptersByBookId(bookId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChapterResponse> updateChapter(@PathVariable Long id, @RequestBody ChapterRequest request) {
        return ResponseEntity.ok(chapterService.updateChapter(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }
    // Lấy chương cuối cùng của một sách
    @GetMapping("/book/{bookId}/last")
    public ResponseEntity<ChapterResponse> getLastChapterByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(chapterService.getLastChapterByBookId(bookId));
    }

    // Lấy chương theo số chương và mã sách
    @GetMapping("/book/{bookId}/number/{chapterNumber}")
    public ResponseEntity<ChapterResponse> getChapterByBookIdAndNumber(
            @PathVariable Long bookId,
            @PathVariable int chapterNumber
    ) {
        return ResponseEntity.ok(chapterService.getChapterByBookIdAndNumber(bookId, chapterNumber));
    }

    // Đếm số chương của sách
    @GetMapping("/book/{bookId}/count")
    public ResponseEntity<Long> countChaptersByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(chapterService.countChaptersByBookId(bookId));
    }

    // Xoá toàn bộ chương của một sách (cẩn thận khi dùng)
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteChaptersByBookId(@PathVariable Long bookId) {
        chapterService.deleteChaptersByBookId(bookId);
        return ResponseEntity.noContent().build();
    }

}
