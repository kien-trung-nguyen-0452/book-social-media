package org.readingservice.controler;

import lombok.RequiredArgsConstructor;
import org.readingservice.dto.common.ApiResponse;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookResponse;
import org.readingservice.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor

public class InternalBookController {
    private final BookService bookService;

    @PostMapping("/create")

    public ApiResponse<BookResponse> createBook(@RequestBody BookRequest request) {
        BookResponse response = bookService.createBook(request);
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .data(response)
                .message("Book created successfully")
                .build();
    }
    @PutMapping("/update/{id}")
    public ApiResponse<BookResponse> updateBook(@PathVariable String id, @RequestBody BookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .data(bookService.updateBook(id, request))
                .message("Book updated successfully")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Book deleted successfully")
                .build();
    }



}
