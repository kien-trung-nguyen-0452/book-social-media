package org.readingservice.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.readingservice.dto.request.BookCreationRequest;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookCreationResponse;
import org.readingservice.dto.response.BookResponse;

import org.readingservice.entity.Book;
import org.readingservice.event.BookEvent;
import org.readingservice.exception.ErrorCode;
import org.readingservice.exception.ServiceException;
import org.readingservice.mapper.BookMapper;
import org.readingservice.repository.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j

@Valid
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    BookMapper bookMapper;
    BookProducerService producerService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BookCreationResponse createBook(BookCreationRequest request) {
        Optional<Book> existingBook = bookRepository.findByTitleAndAuthor(request.getTitle(), request.getAuthor());
        if (existingBook.isPresent()) {
            throw new ServiceException(ErrorCode.DUPLICATE_BOOK);
        }
        Book book = bookMapper.toEntity(request);
        book.setViewCount(0);
        book.setCreatedBy(request.getCreatedBy());
        book.setIsCompleted(request.getIsCompleted());
        book.setChapterCount(0);
        book.setSubtitle(request.getSubtitle());
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCoverUrl(request.getCoverUrl());
        book.setLastUpdatedBy(request.getLastUpdatedBy());
        Book savedBook = bookRepository.save(book);

        BookEvent event = bookMapper.toBookEvent(savedBook);
        producerService.creationEven(event);

        return bookMapper.toBookCreationResponse(savedBook);
    }


    @Override
    public BookResponse getBookById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOOK_NOT_FOUND));
        return bookMapper.toResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        var bookList = bookRepository.findAll();
        if (bookList.isEmpty()) {
            log.info("empty list");
        }
        return bookList
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse updateBook(String id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOOK_NOT_FOUND));
        book.setCreatedBy(request.getCreatedBy());
        book.setIsCompleted(request.getIsCompleted());
        book.setSubtitle(request.getSubtitle());
        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());
        book.setCoverUrl(request.getCoverUrl());
        book.setIsCompleted(request.getIsCompleted());
        book.setCategories(request.getCategories());
        book.setSubtitle(request.getSubtitle());
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOOK_NOT_FOUND));

        // Gửi event trước khi xóa để giữ đủ thông tin nếu cần
        BookEvent event = bookMapper.toBookDeletionEvent(book);
        producerService.deletionEvent(event);
        bookRepository.deleteById(id);
    }


}
