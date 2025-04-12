package org.readingservice.readingservice.service;


import lombok.RequiredArgsConstructor;
import org.readingservice.dto.BookDTO;
import org.readingservice.entity.Book;
import org.readingservice.mapper.BookMapper;
import org.readingservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDTO createBook(BookDTO dto) {
        Book book = BookMapper.toEntity(dto);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return BookMapper.toDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setAuthor(dto.getAuthor());
        book.setUpdatedAt(LocalDateTime.now());

        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

