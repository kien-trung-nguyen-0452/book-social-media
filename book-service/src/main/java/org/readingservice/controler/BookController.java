package org.readingservice.controler;

import lombok.RequiredArgsConstructor;
import org.readingservice.client.dto.chapter.ChapterResponse;
import org.readingservice.dto.common.ApiResponse;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookResponse;
import org.readingservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // ============================ BOOK CRUD ============================

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@RequestBody BookRequest request) {
        BookResponse createdBook = bookService.createBook(request);
        return ResponseEntity.ok(new ApiResponse<>(createdBook, "Book created successfully", 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(new ApiResponse<>(books, "All books retrieved", 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long id) {
        BookResponse book = bookService.getBookById(id);
        return ResponseEntity.ok(new ApiResponse<>(book, "Book found", 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(
            @PathVariable Long id,
            @RequestBody BookRequest request
    ) {
        BookResponse updated = bookService.updateBook(id, request);
        return ResponseEntity.ok(new ApiResponse<>(updated, "Book updated successfully", 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "Book deleted successfully", 200));
    }

    // ============================ BOOK FILTERING ============================

    @GetMapping("/author")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(
                new ApiResponse<>(bookService.getBooksByAuthor(author), "Books by author found", 200)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(
                new ApiResponse<>(bookService.getBooksByCategory(categoryId), "Books by category found", 200)
        );
    }

    @GetMapping("/top-rated")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getTopRatedBooks(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(
                new ApiResponse<>(bookService.getTopRatedBooks(limit), "Top rated books", 200)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookResponse>>> searchBooks(@RequestParam String keyword) {
        return ResponseEntity.ok(
                new ApiResponse<>(bookService.searchBooks(keyword), "Search results", 200)
        );
    }

    // ============================ CHAPTER SERVICE (via OpenFeign) ============================
    @GetMapping("/{bookId}/chapters")
    public ResponseEntity<ApiResponse<List<ChapterResponse>>> getChaptersByBookId(@PathVariable Long bookId) {
        List<ChapterResponse> chapters = bookService.getChaptersByBookId(bookId);
        if (chapters == null || chapters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "No chapters found for the book", 404));
        }
        return ResponseEntity.ok(
                new ApiResponse<>(chapters, "Chapters of book found", 200)
        );
    }

    @GetMapping("/{bookId}/chapters/last")
    public ResponseEntity<ApiResponse<ChapterResponse>> getLastChapter(@PathVariable Long bookId) {
        return ResponseEntity.ok(
                new ApiResponse<>(bookService.getLastChapter(bookId), "Last chapter found", 200)
        );
    }

    @GetMapping("/chapters/{chapterId}")
    public ResponseEntity<ApiResponse<ChapterResponse>> getChapterById(@PathVariable Long chapterId) {
        return ResponseEntity.ok(
                new ApiResponse<>(bookService.getChapterById(chapterId), "Chapter found by ID", 200)
        );
    }

    @GetMapping("/{bookId}/chapters/number/{chapterNumber}")
    public ResponseEntity<ApiResponse<ChapterResponse>> getChapterByBookIdAndNumber(
            @PathVariable Long bookId,
            @PathVariable int chapterNumber
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        bookService.getChapterByBookIdAndNumber(bookId, chapterNumber),
                        "Chapter found by book and number",
                        200
                )
        );
    }
}
