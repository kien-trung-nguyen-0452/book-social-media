package org.example.searchservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.searchservice.dto.ApiResponse;
import org.example.searchservice.dto.response.BookSearchingResult;
import org.example.searchservice.service.BookIndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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
    @GetMapping("/categories")
    ApiResponse<List<BookSearchingResult>> getBookByCategories (@RequestParam List<String> categories){
        return ApiResponse.<List<BookSearchingResult>>builder()
                .code(1000)
                .data(bookIndexService.findByCategories(categories))
                .message("Find all books by categories")
                .build();
    }
    @GetMapping("/author")
    ApiResponse <List<BookSearchingResult> >getBooksByAuthor(@RequestParam("author") String author) {
        return ApiResponse.<List<BookSearchingResult>>builder()
                .code(1000)
                .message("Find all books by author")
                .data(bookIndexService.getAllBookByAuthor(author))
                .build();

    }
    @GetMapping("/title")
    ApiResponse<List<BookSearchingResult>> getBookByTitle (@RequestParam("title") String title){
        return ApiResponse.<List<BookSearchingResult>>builder()
                .code(1000)
                .data(bookIndexService.findByTitleContaining(title))
                .build();
    }

    @GetMapping("/autocomplete")
    public ApiResponse<List<BookSearchingResult>> autocompleteTitle(@RequestParam("prefix") String prefix) {
        List<BookSearchingResult> results = bookIndexService.autocompleteTitle(prefix);

        return ApiResponse.<List<BookSearchingResult>>builder()
                .code(1000)
                .message("Success")
                .data(results)
                .build();
    }

}
