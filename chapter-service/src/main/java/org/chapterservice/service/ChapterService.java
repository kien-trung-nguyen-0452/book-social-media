package org.chapterservice.service;

import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;

import java.util.List;

public interface ChapterService {

    ChapterResponse createChapter(ChapterRequest request);

    ChapterResponse getChapterById(String id); // Đổi Long -> String

    List<ChapterResponse> getChaptersByBookId(Long bookId);

    ChapterResponse updateChapter(String id, ChapterRequest request); // Đổi Long -> String

    void deleteChapter(String id); // Đổi Long -> String

    ChapterResponse getLastChapterByBookId(Long bookId);

    ChapterResponse getChapterByBookIdAndNumber(Long bookId, int chapterNumber);

    long countChaptersByBookId(Long bookId);

    void deleteChaptersByBookId(Long bookId);
}
