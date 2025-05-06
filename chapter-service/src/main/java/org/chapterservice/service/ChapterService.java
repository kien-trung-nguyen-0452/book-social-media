package org.chapterservice.service;

import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;

import java.util.List;

public interface ChapterService {

    ChapterResponse createChapter(ChapterRequest request);

    ChapterResponse getChapterById(Long id);

    List<ChapterResponse> getChaptersByBookId(Long bookId);

    ChapterResponse updateChapter(Long id, ChapterRequest request);

    void deleteChapter(Long id);

    ChapterResponse getLastChapterByBookId(Long bookId);

    ChapterResponse getChapterByBookIdAndNumber(Long bookId, int chapterNumber);

    long countChaptersByBookId(Long bookId);

    void deleteChaptersByBookId(Long bookId);
}
