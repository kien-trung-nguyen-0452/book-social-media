package org.chapterservice.service;

import lombok.RequiredArgsConstructor;
import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.entity.Chapter;
import org.chapterservice.exception.ErrorCode;
import org.chapterservice.exception.ServiceException;
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
        try {
            Chapter chapter = chapterMapper.toEntity(request);
            chapter.setCreatedAt(LocalDateTime.now());
            chapter.setUpdatedAt(LocalDateTime.now());
            return chapterMapper.toResponse(chapterRepository.save(chapter));
        } catch (Exception ex) {
            throw new ServiceException(ErrorCode.INTERNAL_ERROR, ex);
        }
    }

    @Override
    public ChapterResponse getChapterById(Long id) {
        return chapterRepository.findById(id)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new ServiceException(ErrorCode.CHAPTER_NOT_FOUND));
    }

    @Override
    public List<ChapterResponse> getChaptersByBookId(Long bookId) {
        try {
            return chapterRepository.findByBookIdOrderByChapterNumberAsc(bookId)
                    .stream()
                    .map(chapterMapper::toResponse)
                    .toList();
        } catch (Exception ex) {
            throw new ServiceException(ErrorCode.INTERNAL_ERROR, ex);
        }
    }

    @Override
    public ChapterResponse updateChapter(Long id, ChapterRequest request) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.CHAPTER_NOT_FOUND));

        chapter.setTitle(request.getTitle());
        chapter.setContent(request.getContent());
        chapter.setChapterNumber(request.getChapterNumber());
        chapter.setUpdatedAt(LocalDateTime.now());

        return chapterMapper.toResponse(chapterRepository.save(chapter));
    }

    @Override
    public void deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) {
            throw new ServiceException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        chapterRepository.deleteById(id);
    }
    @Override
    public ChapterResponse getLastChapterByBookId(Long bookId) {
        return chapterRepository.findFirstByBookIdOrderByChapterNumberDesc(bookId)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("No chapters found for book ID: " + bookId));
    }

    @Override
    public ChapterResponse getChapterByBookIdAndNumber(Long bookId, int chapterNumber) {
        return chapterRepository.findByBookIdAndChapterNumber(bookId, chapterNumber)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Chapter " + chapterNumber + " not found for book ID: " + bookId));
    }

    @Override
    public long countChaptersByBookId(Long bookId) {
        return chapterRepository.countByBookId(bookId);
    }

    @Override
    public void deleteChaptersByBookId(Long bookId) {
        chapterRepository.deleteByBookId(bookId);
    }

}
