package org.readingservice.service;


import org.readingservice.dto.BookResponse;
import org.readingservice.dto.BookRequest;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest request);
    BookResponse getBookById(Long id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);

    List<BookResponse> getBooksByAuthor(String author);
    List<BookResponse> getBooksByCategory(Long categoryId);
    List<BookResponse> getTopRatedBooks(int limit);
    List<BookResponse> searchBooks(String keyword);
}

