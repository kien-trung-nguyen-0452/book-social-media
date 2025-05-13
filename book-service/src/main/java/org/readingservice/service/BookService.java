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

    // ======================= CRUD Books =======================
    BookResponse createBook(BookRequest request);
    BookResponse getBookById(String id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(String id, BookRequest request);
    void deleteBook(String id); // Đã sửa từ Long -> String

}
