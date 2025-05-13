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
    public ApiResponse<BookResponse> getBookById(@PathVariable String id) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.getBookById(id))
                .message("Book found")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BookResponse> updateBook(@PathVariable String id, @RequestBody BookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.updateBook(id, request))
                .message("Book updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ApiResponse.<Void>builder()
                .message("Book deleted successfully")
                .build();
    }


}
