package org.readingservice.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.readingservice.dto.common.ApiResponse;

import org.readingservice.dto.response.BookResponse;
import org.readingservice.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
