package org.chapterservice.service;

import org.chapterservice.dto.ChapterRequest;
import org.chapterservice.dto.ChapterResponse;

import java.util.List;

public interface ChapterService {
    ChapterResponse createChapter(ChapterRequest request);
    ChapterResponse getChapterById(Long id);
    List<ChapterResponse> getChaptersByBookId(Long bookId);
    ChapterResponse updateChapter(Long id, ChapterRequest request);
    void deleteChapter(Long id);
}
