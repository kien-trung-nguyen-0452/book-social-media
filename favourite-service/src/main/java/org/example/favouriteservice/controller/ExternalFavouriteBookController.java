package org.example.favouriteservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.favouriteservice.dto.ApiResponse;
import org.example.favouriteservice.dto.response.FavouriteCountResponse;
import org.example.favouriteservice.service.FavouriteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/external")
@Tag(name = "External API for favourite service")
public class ExternalFavouriteBookController {
    FavouriteService service;
    @Operation(
            method = "GET",
            summary = "Total number of times the story has been favorite")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "0",
                            description = "success"),
            })
    @GetMapping("count/{bookId}")
    public ApiResponse<FavouriteCountResponse> getCountByBookId(@PathVariable String bookId){
        var result = FavouriteCountResponse.builder().countNumber(service.getCountByBookId(bookId)).build();
        return ApiResponse.<FavouriteCountResponse>builder()
                .result(result)
                .build();
    }
}
