package org.readingservice.service;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.readingservice.client.ChapterClient;
import org.readingservice.client.ChapterResponse;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.entity.Book;
import org.readingservice.exception.ErrorCode;
import org.readingservice.exception.ServiceException;
import org.readingservice.mapper.BookMapper;
import org.readingservice.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.readingservice.dto.response.BookResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Valid
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


    @Override
    public ChapterResponse getChapterInfo(Long bookId, Long chapterId) {
        try {
            ChapterResponse chapter = chapterClient.getChapterById(chapterId);
            if (!chapter.getBookId().equals(bookId)) {
                throw new ServiceException(ErrorCode.CHAPTER_NOT_IN_BOOK);
            }
            return chapter;
        } catch (Exception ex) {
            throw new ServiceException(ErrorCode.CHAPTER_SERVICE_ERROR, ex);
        }
    }

    @Override
    public List<ChapterResponse> getChaptersByBookId(Long bookId) {
        try {
            return chapterClient.getChaptersByBookId(bookId);
        } catch (Exception ex) {
            throw new ServiceException(ErrorCode.BOOK_NOT_FOUND, ex);
        }
    }
    @Override
    public ChapterResponse getLastChapter(Long bookId) {
        return chapterClient.getLastChapterByBookId(bookId);
    }

    @Override
    public ChapterResponse getChapterByNumber(Long bookId, int chapterNumber) {
        return chapterClient.getChapterByBookIdAndNumber(bookId, chapterNumber);
    }

    @Override
    public Long countChapters(Long bookId) {
        return chapterClient.countChaptersByBookId(bookId);
    }

    @Override
    public void deleteAllChapters(Long bookId) {
        chapterClient.deleteChaptersByBookId(bookId);
    }

}