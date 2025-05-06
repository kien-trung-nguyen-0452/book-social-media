package org.bookmarkservice.service;

import org.bookmarkservice.dto.request.BookmarkRequest;
import org.bookmarkservice.dto.response.BookmarkResponse;

import java.util.List;

public interface BookmarkService {
    BookmarkResponse createBookmark(BookmarkRequest request);
    List<BookmarkResponse> getBookmarksByUserId(Long userId);
    void deleteBookmark(BookmarkRequest request);
    boolean existsBookmark(Long userId, Long bookId);
}
