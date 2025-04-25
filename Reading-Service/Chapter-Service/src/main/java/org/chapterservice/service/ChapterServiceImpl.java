package org.chapterservice.service;

import lombok.RequiredArgsConstructor;
import org.chapterservice.dto.ChapterRequest;
import org.chapterservice.dto.ChapterResponse;
import org.chapterservice.entity.Chapter;
import org.chapterservice.mapper.ChapterMapper;
import org.chapterservice.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public ChapterResponse createChapter(ChapterRequest request) {
        Chapter chapter = chapterMapper.toEntity(request);
        chapter.setCreatedAt(LocalDateTime.now());
        chapter.setUpdatedAt(LocalDateTime.now());
        return chapterMapper.toResponse(chapterRepository.save(chapter));
    }

    @Override
    public ChapterResponse getChapterById(Long id) {
        return chapterRepository.findById(id)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
    }

    @Override
    public List<ChapterResponse> getChaptersByBookId(Long bookId) {
        return chapterRepository.findByBookIdOrderByChapterNumberAsc(bookId)
                .stream()
                .map(chapterMapper::toResponse)
                .toList();
    }

    @Override
    public ChapterResponse updateChapter(Long id, ChapterRequest request) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        chapter.setTitle(request.getTitle());
        chapter.setContent(request.getContent());
        chapter.setChapterNumber(request.getChapterNumber());
        chapter.setUpdatedAt(LocalDateTime.now());
        return chapterMapper.toResponse(chapterRepository.save(chapter));
    }

    @Override
    public void deleteChapter(Long id) {
        chapterRepository.deleteById(id);
    }
}
