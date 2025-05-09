package org.readingservice.service;

import org.readingservice.repository.httpClient.dto.analytics.AnalyticsRequest;
import org.readingservice.repository.httpClient.dto.analytics.AnalyticsResponse;
import org.readingservice.repository.httpClient.dto.chapter.ChapterResponse;
import org.readingservice.repository.httpClient.dto.comment.CommentRequest;
import org.readingservice.repository.httpClient.dto.comment.CommentResponse;
import org.readingservice.repository.httpClient.dto.wishlist.WishlistResponse;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookResponse;

import java.util.List;

public interface BookService {

    // ======================= CRUD và xử lý nội bộ =======================
    BookResponse createBook(BookRequest request);
    BookResponse getBookById(Long id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);

    // ======================= Truy vấn nội bộ =======================
    List<BookResponse> getBooksByAuthor(String author);
    List<BookResponse> getBooksByCategory(Long categoryId);
    List<BookResponse> getTopRatedBooks(int limit);
    List<BookResponse> searchBooks(String keyword);

    // ======================= Gọi CHAPTER SERVICE (Feign) =======================
    List<ChapterResponse> getChaptersByBookId(Long bookId);
    ChapterResponse getLastChapter(Long bookId);
    ChapterResponse getChapterById(Long chapterId);
    ChapterResponse getChapterByBookIdAndNumber(Long bookId, int chapterNumber);

    // ======================= Gọi WISHLIST SERVICE (Feign) =======================
    List<WishlistResponse> getWishlistByUser(Long userId);
    public void removeBookFromWishlist(Long userId, Long bookId);

    // ======================= Gọi COMMENT SERVICE (Feign) =======================
    List<CommentResponse> getCommentsByChapterId(Long chapterId);
    CommentResponse addComment(CommentRequest request);
    void deleteComment(String commentId);

    // ======================= Gọi ANALYTICS SERVICE (Feign) =======================
    void trackReading(AnalyticsRequest request);
    List<AnalyticsResponse> getUserReadingStats(Long userId);
}
