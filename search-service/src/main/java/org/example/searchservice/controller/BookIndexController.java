package org.example.searchservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.searchservice.dto.ApiResponse;
import org.example.searchservice.dto.request.BookIndexUpdateRequest;
import org.example.searchservice.dto.response.BookIndexResponse;
import org.example.searchservice.dto.response.BookSearchingResult;
import org.example.searchservice.service.BookIndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/search")
public class BookIndexController {
    BookIndexService bookIndexService;

    @GetMapping
    ApiResponse<List<BookSearchingResult>> getAll (){
        return ApiResponse.<List<BookSearchingResult>>builder()
                .code(1000)
                .data(bookIndexService.getAll())
                .build();
    }

    @PutMapping("/update_book")
    ApiResponse<BookIndexResponse> updateBook (BookIndexUpdateRequest request){
        return ApiResponse.<BookIndexResponse>builder()
                .code(1000)
                .data(bookIndexService.updateBookIndex(request))
                . build();
    }

}
