package org.chapterservice.service;

import lombok.RequiredArgsConstructor;
import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.entity.Chapter;
import org.chapterservice.exception.ErrorCode;
import org.chapterservice.exception.ServiceException;
import org.chapterservice.mapper.ChapterMapper;
import org.chapterservice.repository.ChapterRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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
            LocalDateTime now = LocalDateTime.now();
            chapter.setCreatedAt(now);
            chapter.setUpdatedAt(now);

            chapter.setBookId(request.getBookId()); // set bookId
            chapter.setImageUrl(request.getImages()); // set danh sách ảnh đúng tên trường
            chapter.setChapter(request.getChapter());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                username = SecurityContextHolder.getContext().getAuthentication().getName();
            }
            System.out.println("Current username: " + username);

            chapter.setCreatedBy(username);
            chapter.setUpdatedBy(username );
            return chapterMapper.toResponse(chapterRepository.save(chapter));
        } catch (Exception ex) {
            throw new ServiceException(ErrorCode.INTERNAL_ERROR, ex);
        }
    }

    @Override
    public ChapterResponse getChapterById(String id) {
        return chapterRepository.findById(id)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new ServiceException(ErrorCode.CHAPTER_NOT_FOUND));
    }

    @Override
    public List<ChapterResponse> getChaptersByBookId(String bookId) {
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
    public ChapterResponse updateChapter(String id, ChapterRequest request) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.CHAPTER_NOT_FOUND));

        chapter.setTitle(request.getTitle());
        chapter.setContent(request.getContent());
        chapter.setChapterNumber(request.getChapterNumber());

        chapter.setBookId(request.getBookId()); // cập nhật bookId nếu có
        chapter.setImageUrl(request.getImages()); // cập nhật ảnh
        chapter.setChapter(request.getChapter());  // cập nhật tên chương

        chapter.setUpdatedAt(LocalDateTime.now());


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        System.out.println("Current username: " + username);
        chapter.setUpdatedBy(username);

        return chapterMapper.toResponse(chapterRepository.save(chapter));
    }

    @Override
    public void deleteChapter(String id) {
        if (!chapterRepository.existsById(id)) {
            throw new ServiceException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        chapterRepository.deleteById(id);
    }

    @Override
    public ChapterResponse getLastChapterByBookId(String bookId) {
        return chapterRepository.findFirstByBookIdOrderByChapterNumberDesc(bookId)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("No chapters found for book ID: " + bookId));
    }

    @Override
    public ChapterResponse getChapterByBookIdAndNumber(String bookId, int chapterNumber) {
        return chapterRepository.findByBookIdAndChapterNumber(bookId, chapterNumber)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Chapter " + chapterNumber + " not found for book ID: " + bookId));
    }

    @Override
    public long countChaptersByBookId(String bookId) {
        return chapterRepository.countByBookId(bookId);
    }

    @Override
    public void deleteChaptersByBookId(String bookId) {
        chapterRepository.deleteByBookId(bookId);
    }
}
