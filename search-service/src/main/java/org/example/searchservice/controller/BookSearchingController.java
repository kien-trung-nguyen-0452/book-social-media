package org.example.searchservice.controller;

import org.example.searchservice.dto.ApiResponse;
import org.example.searchservice.dto.response.BookSearchingResult;
import org.example.searchservice.service.BookIndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class BookSearchingController {
    BookIndexService bookIndexService;

    @GetMapping
    ApiResponse<List<BookSearchingResult>> getAll (){
        return ApiResponse.<List<BookSearchingResult>>builder()
                .code(1000)
                .data(bookIndexService.getAll())
                .build();
    }

}
