package org.chapterservice.service;

import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ChapterService {

    ChapterResponse createChapter(String bookId,ChapterRequest request);

    ChapterResponse getChapterById(String id); // Đổi Long -> String

    List<ChapterResponse> getChaptersByBookId(String bookId);

    ChapterResponse updateChapter(String id, ChapterRequest request); // Đổi Long -> String

    void deleteChapter(String id); // Đổi Long -> String

    ChapterResponse getLastChapterByBookId(String bookId);

    ChapterResponse getChapterByBookIdAndNumber(String bookId, int chapterNumber);

    long countChaptersByBookId(String bookId);

    void deleteChaptersByBookId(String bookId);

}
