package org.readingservice.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.readingservice.dto.request.BookRequest;
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
    public BookResponse createBook(BookRequest request) {
        Book book = bookMapper.toEntity(request);
        book.setViewCount(0);
        book.setAverageRating(0.0);
        book.setChapterCount(0);
        book.setSubtitle(request.getSubtitle());
        Book savedBook = bookRepository.save(book);
        BookEvent event = bookMapper.toBookEvent(savedBook);
        event.setCategories(request.getCategories());
        producerService.creationEven(event);

        return bookMapper.toResponse(savedBook);
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
        if (!bookRepository.existsById(id)) {
            throw new ServiceException(ErrorCode.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }


}
