package org.readingservice.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.readingservice.dto.common.ApiResponse;

import org.readingservice.dto.response.BookResponse;
import org.readingservice.entity.Book;
import org.readingservice.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "External Book APIs ", description = "Using for reading")
public class BookController {

    private final BookService bookService;

    @GetMapping("/all")
    @Operation(method = "GET", summary = "Get all books in database",
            security = @SecurityRequirement(name = "none"))
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1000", description = "Request Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2001", description = "Book not found")
    })
    public ApiResponse<List<BookResponse>> getAllBooks() {
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .data(bookService.getAllBooks())
                .message("All books retrieved")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID",
            description = "Retrieve a book by its unique ID",
            security = @SecurityRequirement(name = "none"))
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1000", description = "Book found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2001", description = "Book not found")
    })
    public ApiResponse<BookResponse> getBookById(@PathVariable String id) {
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .data(bookService.getBookById(id))
                .message("Book found")
                .build();
    }

    @GetMapping("/by-created-date")
    @Operation(
            summary = "Get books sorted by created date",
            description = "Retrieve books ordered by their creation date descending",
            security = @SecurityRequirement(name = "none")
    )
    public ApiResponse<List<BookResponse>> getBooksOrderByCreatedDateDesc() {
        List<BookResponse> responses = bookService.getBooksSortedByCreatedDate();
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Books sorted by created date")
                .build();
    }

    @GetMapping("/by-view-count")
    @Operation(
            summary = "Get books sorted by view count",
            description = "Retrieve books ordered by their view count descending",
            security = @SecurityRequirement(name = "none")
    )
    public ApiResponse<List<BookResponse>> getBooksOrderByViewCountDesc() {
        List<BookResponse> responses = bookService.getBooksSortedByViewCount();
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Books sorted by view count")
                .build();
    }



}
