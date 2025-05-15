package org.example.searchservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.searchservice.dto.ApiResponse;
import org.example.searchservice.dto.response.BookSearchingResult;
import org.example.searchservice.service.BookIndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/search")
public class BookSearchingController {
    BookIndexService bookIndexService;

    @GetMapping("/all")
    ApiResponse<List<BookSearchingResult>> getAll (){
        return ApiResponse.<List<BookSearchingResult>>builder()
                .code(1000)
                .data(bookIndexService.getAll())
                .build();
    }

}
