package org.readingservice.controler;


import lombok.RequiredArgsConstructor;
import org.readingservice.dto.BookRequest;
import org.readingservice.dto.BookResponse;
import org.readingservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Create a new book
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest request) {
        BookResponse createdBook = bookService.createBook(request);
        return ResponseEntity.ok(createdBook);
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        BookResponse book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Update book
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody BookRequest request
    ) {
        BookResponse updated = bookService.updateBook(id, request);
        return ResponseEntity.ok(updated);
    }

    // Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/author")
    public List<BookResponse> getBooksByAuthor(@RequestParam String author) {
        return bookService.getBooksByAuthor(author);
    }

    //  Tìm theo thể loại
    @GetMapping("/category/{categoryId}")
    public List<BookResponse> getBooksByCategoryId(@PathVariable Long categoryId) {
        return bookService.getBooksByCategory(categoryId);
    }

    //  Top đánh giá cao
    @GetMapping("/top-rated")
    public List<BookResponse> getTopRatedBooks(@RequestParam(defaultValue = "10") int limit) {
        return bookService.getTopRatedBooks(limit);
    }

    //  Tìm theo từ khóa
    @GetMapping("/search")
    public List<BookResponse> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }
}