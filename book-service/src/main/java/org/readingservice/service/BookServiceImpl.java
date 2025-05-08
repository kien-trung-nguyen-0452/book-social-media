package org.readingservice.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.readingservice.repository.httpClient.AnalyticsServiceClient;
import org.readingservice.repository.httpClient.ChapterServiceClient;
import org.readingservice.repository.httpClient.CommentServiceClient;
import org.readingservice.repository.httpClient.WishlistServiceClient;
import org.readingservice.repository.httpClient.dto.analytics.AnalyticsRequest;
import org.readingservice.repository.httpClient.dto.analytics.AnalyticsResponse;
import org.readingservice.repository.httpClient.dto.chapter.ChapterResponse;
import org.readingservice.repository.httpClient.dto.wishlist.WishlistResponse;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookResponse;
import org.readingservice.repository.httpClient.dto.comment.CommentResponse;
import org.readingservice.repository.httpClient.dto.comment.CommentRequest;
import org.readingservice.entity.Book;
import org.readingservice.exception.ErrorCode;
import org.readingservice.exception.ServiceException;
import org.readingservice.mapper.BookMapper;
import org.readingservice.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Valid
@Service
@RequiredArgsConstructor

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ChapterServiceClient chapterClient;
    private final WishlistServiceClient wishlistClient;
    private final CommentServiceClient commentServiceClient;
    private final AnalyticsServiceClient analyticsServiceClient;
    // =========================== CRUD BOOKS - Dùng nội bộ ===========================

    @Override
    public BookResponse createBook(BookRequest request) {
        Book book = bookMapper.toEntity(request);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setViewCount(0L);
        book.setAverageRating(0.0);
        book.setChapterCount(0); // default ban đầu
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOOK_NOT_FOUND));
        return bookMapper.toResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOOK_NOT_FOUND));
        book.setTitle(request.getTitle());
        book.setSubtitle(request.getSubtitle());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());
        book.setCoverUrl(request.getCoverUrl());
        book.setIsCompleted(request.getIsCompleted());
        book.setCategoryId(request.getCategoryId());
        book.setUpdatedAt(LocalDateTime.now());
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ServiceException(ErrorCode.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    // =========================== Tìm kiếm - Dùng nội bộ ===========================

    @Override
    public List<BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByCategory(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId)
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getTopRatedBooks(int limit) {
        return bookRepository.findTopRatedBooks(PageRequest.of(0, limit))
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword.toLowerCase())
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    // =========================== Gọi CHAPTER SERVICE qua Feign ===========================

    @Override
    public List<ChapterResponse> getChaptersByBookId(Long bookId) {
        return chapterClient.getChaptersByBookId(bookId).getData();
    }

    @Override
    public ChapterResponse getLastChapter(Long bookId) {
        return chapterClient.getLastChapterByBookId(bookId).getData();
    }
    @Override
    public ChapterResponse getChapterById(Long chapterId) {
        return chapterClient.getChapterById(chapterId).getData();
    }

    @Override
    public ChapterResponse getChapterByBookIdAndNumber(Long bookId, int chapterNumber) {
        return chapterClient.getChapterByBookIdAndNumber(bookId, chapterNumber).getData();
    }
    // =========================== Gọi WISHLIST SERVICE qua Feign ===========================
    @Override
    public List<WishlistResponse> getWishlistByUser(Long userId) {
        return wishlistClient.getWishlistByUser(userId).getData();
    }

    @Override
    public void removeBookFromWishlist(Long userId, Long bookId) {
        wishlistClient.removeFromWishlist(userId, bookId);
    }
// =========================== Gọi COMMENT SERVICE qua Feign ===========================
    @Override
    public List<CommentResponse> getCommentsByChapterId(Long chapterId) {
        return commentServiceClient.getCommentsByChapterId(chapterId).getData();
    }

    @Override
    public CommentResponse addComment(CommentRequest request) {
        return commentServiceClient.addComment(request).getData();
    }

    @Override
    public void deleteComment(String commentId) {
        commentServiceClient.deleteComment(commentId);
    }
// =========================== Gọi ANALYTICS SERVICE qua Feign ===========================
    @Override
    public void trackReading(AnalyticsRequest request) {
        analyticsServiceClient.trackReading(request); // Gọi API track reading
}

    @Override
    public List<AnalyticsResponse> getUserReadingStats(Long userId) {
        return analyticsServiceClient.getUserReadingStats(userId).getData();
    }
}


