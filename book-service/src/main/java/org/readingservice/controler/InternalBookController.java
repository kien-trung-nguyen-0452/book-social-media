package org.readingservice.controler;

import lombok.RequiredArgsConstructor;
import org.readingservice.dto.common.ApiResponse;
import org.readingservice.dto.request.BookCreationRequest;
import org.readingservice.dto.request.BookRequest;
import org.readingservice.dto.response.BookCreationResponse;
import org.readingservice.dto.response.BookResponse;
import org.readingservice.repository.BookRepository;
import org.readingservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalBookController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @PostMapping("/create")
    @io.swagger.v3.oas.annotations.Operation(summary = "Create a new book",
            description = "Create a new book record with the provided details",
            security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "none"))
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1000", description = "Book created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "9999", description = "Invalid request data")
    })
    public ApiResponse<BookCreationResponse> createBook(@RequestBody BookCreationRequest request) {
        BookCreationResponse response = bookService.createBook(request);
        return ApiResponse.<BookCreationResponse>builder()
                .code(1000)
                .data(response)
                .message("Book created successfully")
                .build();
    }

    @PutMapping("/update/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Update an existing book",
            description = "Update book details by book ID",
            security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "none"))
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1000", description = "Book updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2001", description = "Book not found")
    })
    public ApiResponse<BookResponse> updateBook(@PathVariable String id, @RequestBody BookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .data(bookService.updateBook(id, request))
                .message("Book updated successfully")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Delete a book",
            description = "Delete book by book ID",
            security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "none"))
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1000", description = "Book deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2001", description = "Book not found")
    })
    public ApiResponse<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Book deleted successfully")
                .build();
    }

    @GetMapping("/{bookId}/exists")
    @io.swagger.v3.oas.annotations.Operation(summary = "Check if a book exists",
            description = "Check if a book exists by ID",
            security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "none"))
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Check completed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2001", description = "Book not found")
    })
    public ApiResponse<Boolean> checkBookExists(@PathVariable String bookId) {
        boolean exists = bookRepository.existsById(bookId);
        if (!exists) {
            return ApiResponse.<Boolean>builder()
                    .code(2001)
                    .data(false)
                    .message("Book not found")
                    .build();
        }
        return ApiResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("Book exists")
                .build();
    }

}
