package org.readingservice.controler;

import lombok.RequiredArgsConstructor;
import org.readingservice.repository.httpClient.dto.chapter.ChapterResponse;
import org.readingservice.dto.common.ApiResponse;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookResponse;
import org.readingservice.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // ============================ BOOK CRUD ============================

    @PostMapping
    public ApiResponse<BookResponse> createBook(@RequestBody BookRequest request) {
        BookResponse response = bookService.createBook(request);

        return ApiResponse.<BookResponse>builder()
                .data(response)
                .message("Book created successfully")
                .build();
    }


    @GetMapping
    public ApiResponse<List<BookResponse>> getAllBooks() {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.getAllBooks())
                .message("All books retrieved")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BookResponse> getBookById(@PathVariable Long id) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.getBookById(id))
                .message("Book found")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.updateBook(id, request))
                .message("Book updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ApiResponse.<Void>builder()
                .message("Book deleted successfully")
                .build();
    }

    // ============================ BOOK FILTERING ============================

    @GetMapping("/author")
    public ApiResponse<List<BookResponse>> getBooksByAuthor(@RequestParam String author) {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.getBooksByAuthor(author))
                .message("Books by author found")
                .build();
    }

    @GetMapping("/category/{category}")
    public ApiResponse<List<BookResponse>> getBooksByCategory(@PathVariable String category) {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.getBooksByCategory(category))
                .message("Books by category found")
                .build();
    }

    @GetMapping("/top-rated")
    public ApiResponse<List<BookResponse>> getTopRatedBooks(@RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.getTopRatedBooks(limit))
                .message("Top rated books")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<BookResponse>> searchBooks(@RequestParam String keyword) {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.searchBooks(keyword))
                .message("Search results")
                .build();
    }

    // ============================ CHAPTER SERVICE (via OpenFeign) ============================

    @GetMapping("/{bookId}/chapters")
    public ApiResponse<List<ChapterResponse>> getChaptersByBookId(@PathVariable Long bookId) {
        List<ChapterResponse> chapters = bookService.getChaptersByBookId(bookId);
        if (chapters == null || chapters.isEmpty()) {
            return ApiResponse.<List<ChapterResponse>>builder()
                    .message("No chapters found for the book")
                    .build();
        }
        return ApiResponse.<List<ChapterResponse>>builder()
                .data(chapters)
                .message("Chapters of book found")
                .build();
    }

    @GetMapping("/{bookId}/chapters/last")
    public ApiResponse<ChapterResponse> getLastChapter(@PathVariable Long bookId) {
        return ApiResponse.<ChapterResponse>builder()
                .data(bookService.getLastChapter(bookId))
                .message("Last chapter found")
                .build();
    }

    @GetMapping("/chapters/{chapterId}")
    public ApiResponse<ChapterResponse> getChapterById(@PathVariable Long chapterId) {
        return ApiResponse.<ChapterResponse>builder()
                .data(bookService.getChapterById(chapterId))
                .message("Chapter found by ID")
                .build();
    }

    @GetMapping("/{bookId}/chapters/number/{chapterNumber}")
    public ApiResponse<ChapterResponse> getChapterByBookIdAndNumber(
            @PathVariable Long bookId,
            @PathVariable int chapterNumber
    ) {
        return ApiResponse.<ChapterResponse>builder()
                .data(bookService.getChapterByBookIdAndNumber(bookId, chapterNumber))
                .message("Chapter found by book and number")
                .build();
    }
}
