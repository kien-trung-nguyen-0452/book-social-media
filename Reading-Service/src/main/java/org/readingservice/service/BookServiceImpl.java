package org.readingservice.service;


import lombok.RequiredArgsConstructor;
import org.readingservice.client.ChapterClient;
import org.readingservice.client.ChapterResponse;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.ChapterDTO;
import org.readingservice.entity.Book;
import org.readingservice.mapper.BookMapper;
import org.readingservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.readingservice.dto.response.BookResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ChapterClient chapterClient;
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
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
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
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
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
            throw new RuntimeException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }
    @Override
    public List<BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();
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
                .map(bookMapper::toBookResponse)
                .toList();
    }

    @Override
    public List<BookResponse> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword.toLowerCase())
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }


    @Override
    public ChapterDTO getChapterInfo(Long chapterId) {
        return chapterClient.getChapterById(chapterId);
    }
    @Override
    public List<ChapterResponse> getChaptersByBookId(Long bookId) {
        return chapterClient.getChaptersByBookId(bookId);
    }

}