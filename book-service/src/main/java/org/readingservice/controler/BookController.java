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
public class  BookController {

    private final BookService bookService;

    @GetMapping("/all")
    @Operation(method = "GET", summary = "Get all books in database"
            ,security = @SecurityRequirement(name= "none"))
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1000",description = "Request Success")
    })
    public ApiResponse<List<BookResponse>> getAllBooks() {
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .data(bookService.getAllBooks())
                .message("All books retrieved")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BookResponse> getBookById(@PathVariable String id) {
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .data(bookService.getBookById(id))
                .message("Book found")
                .build();
    }
    @GetMapping("/by-created-date")
    public ApiResponse<List<BookResponse>> getBooksOrderByCreatedDateDesc() {
        List<Book> books = bookService.getBooksOrderByCreatedDateDesc();
        List<BookResponse> responses = books.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Books sorted by created date")
                .build();
    }

    @GetMapping("/by-view-count")
    public ApiResponse<List<BookResponse>> getBooksOrderByViewCountDesc() {
        List<Book> books = bookService.getBooksOrderByViewCountDesc();
        List<BookResponse> responses = books.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Books sorted by view count")
                .build();
    }

    private BookResponse convertToResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setViewCount(book.getViewCount());
        // Thêm các trường khác nếu có trong BookResponse
        return response;
    }


}
