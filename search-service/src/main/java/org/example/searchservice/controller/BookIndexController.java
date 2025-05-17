package org.example.searchservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.searchservice.dto.ApiResponse;
import org.example.searchservice.dto.request.BookIndexUpdateRequest;
import org.example.searchservice.dto.response.BookIndexResponse;
import org.example.searchservice.dto.response.BookSearchingResult;
import org.example.searchservice.service.BookIndexService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/indexing")
public class BookIndexController {
    BookIndexService bookIndexService;

    @PutMapping("/update_book")
    ApiResponse<BookIndexResponse> updateBook (@RequestBody BookIndexUpdateRequest request){
        return ApiResponse.<BookIndexResponse>builder()
                .code(1000)
                .data(bookIndexService.updateBookIndex(request))
                . build();
    }

}
