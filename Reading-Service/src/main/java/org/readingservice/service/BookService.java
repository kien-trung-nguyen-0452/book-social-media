package org.readingservice.service;


import org.readingservice.client.ChapterResponse;
import org.readingservice.dto.response.BookResponse;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.ChapterDTO;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest request);
    BookResponse getBookById(Long id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
    ChapterDTO getChapterInfo(Long chapterId);
    List<BookResponse> getBooksByAuthor(String author);
    List<BookResponse> getBooksByCategory(Long categoryId);
    List<BookResponse> getTopRatedBooks(int limit);
    List<ChapterResponse> getChaptersByBookId(Long bookId);
    List<BookResponse> searchBooks(String keyword);
}


