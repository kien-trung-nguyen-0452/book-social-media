package org.readingservice.service;

import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest request);
    BookResponse getBookById(String id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(String id, BookRequest request);
    void deleteBook(String id); // ✅ đổi Long -> String
}

