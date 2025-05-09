package org.bookmarkservice.service;

import lombok.RequiredArgsConstructor;
import org.bookmarkservice.dto.request.BookmarkRequest;
import org.bookmarkservice.dto.response.BookmarkResponse;
import org.bookmarkservice.entity.Bookmark;
import org.bookmarkservice.exception.ErrorCode;
import org.bookmarkservice.exception.ServiceException;
import org.bookmarkservice.mapper.BookmarkMapper;
import org.bookmarkservice.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkMapper bookmarkMapper;

    @Override
    public BookmarkResponse createBookmark(BookmarkRequest request) {
        Bookmark bookmark = bookmarkMapper.toEntity(request);
        bookmark.setCreatedAt(LocalDateTime.now());
        bookmarkRepository.save(bookmark);
        return bookmarkMapper.toResponse(bookmark);
    }

    @Override
    public List<BookmarkResponse> getBookmarksByUserId(Long userId) {
        return bookmarkRepository.findByUserId(userId)
                .stream()
                .map(bookmarkMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBookmark(BookmarkRequest request) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndBookId(request.getUserId(), request.getBookId())
                .orElseThrow(() -> new ServiceException(ErrorCode.BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);
    }


    @Override
    public boolean existsBookmark(Long userId, Long bookId) {
        return bookmarkRepository.existsByUserIdAndBookId(userId, bookId);
    }
}
