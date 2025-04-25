package org.chapterservice.controller;

import lombok.RequiredArgsConstructor;
import org.chapterservice.dto.ChapterRequest;
import org.chapterservice.dto.ChapterResponse;
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
}
