package org.readingservice.service;

import org.readingservice.dto.request.BookCreationRequest;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookCreationResponse;
import org.readingservice.dto.response.BookResponse;
import org.readingservice.entity.Book;

import java.util.List;

public interface BookService {
    BookCreationResponse createBook(BookCreationRequest request);
    BookResponse getBookById(String id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(String id, BookRequest request);
    void deleteBook(String id); // ✅ đổi Long -> String
    List<BookResponse> getBooksSortedByCreatedDate();
    public List<BookResponse> getBooksSortedByViewCount();
}

