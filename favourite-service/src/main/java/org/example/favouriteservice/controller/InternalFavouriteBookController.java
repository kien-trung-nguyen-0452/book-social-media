package org.example.favouriteservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.favouriteservice.dto.ApiResponse;
import org.example.favouriteservice.dto.request.BookAddRequest;
import org.example.favouriteservice.dto.request.BookRemoveRequest;
import org.example.favouriteservice.dto.response.BookAddResponse;
import org.example.favouriteservice.dto.response.BookRemoveResponse;
import org.example.favouriteservice.dto.response.FavouriteCountResponse;
import org.example.favouriteservice.dto.response.FavouriteListResponse;
import org.example.favouriteservice.service.FavouriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/internal")
@Tag(name = "Internal API for favourite service", description = "Only using when user logged in")
public class InternalFavouriteBookController {
    FavouriteService service;
    @Operation(
            method = "GET",
            summary = "Get all favourited books by user",
            security = {
                    @SecurityRequirement(name = "BearToken", scopes = {
                           "this user"
                    })
            }
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2005", description = "User not existed")
    })
    @GetMapping("/get-favourite-list/{username}")
    public ApiResponse<List<FavouriteListResponse>> getFavouriteListByUserId(@PathVariable String username) {
        return ApiResponse.<List<FavouriteListResponse>>builder()
                .result(service.getFavouriteListByUsername(username))
                .build();
    }

    @Operation(
            method = "POST",
            summary = "Add a book to user's favourite list",
            security = {
                    @SecurityRequirement(name = "BearToken", scopes = {
                            "this user"
                    })
            }
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2009", description = "Book already in user's favourite list")
    })
    @PostMapping("/add")
    public ApiResponse<BookAddResponse> addBookToFavourite(@RequestBody BookAddRequest request) {
        return ApiResponse.<BookAddResponse>builder()
                .result(service.addBookToFavouriteList(request))
                .build();
    }

    @Operation(
            method = "POST",
            summary = "Remove a book from user's favourite list",
            security = {
                    @SecurityRequirement(name = "BearToken", scopes = {
                            "this user"
                    })
            }
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2005", description = "User not existed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2007", description = "Book not found in favourite list")
    })
    @PostMapping("/remove")
    public ApiResponse<BookRemoveResponse> removeBookFromFavourite(@RequestBody BookRemoveRequest request) {
        return ApiResponse.<BookRemoveResponse>builder()
                .result(service.removeBookFromFavouriteList(request))
                .build();
    }

    @Operation(
            method = "GET",
            summary = "Check if a book is in user's favourite list",
            security = {
                    @SecurityRequirement(name = "BearToken", scopes = {
                            "this user"
                    })
            }

    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2005", description = "User not existed")
    })
    @GetMapping("/is-favourite")
    public ApiResponse<Boolean> isFavourite(@RequestParam String username, @RequestParam String bookId) {
        return ApiResponse.<Boolean>builder()
                .result(service.isFavouriteByUser(username, bookId))
                .build();
    }

    @Operation(
            method = "DELETE",
            summary = "Remove all favourite books by user",
            security = {
                    @SecurityRequirement(name = "BearToken", scopes = {
                            "this user", "ADMIN"
                    })
            }
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2007", description = "No book was found")
    })
    @DeleteMapping("/remove-all-by-user/{username}")
    public ApiResponse<Void> removeAllFavouritesByUser(@PathVariable String username) {
        service.removeAllFavouriteBookByUsername(username);
        return ApiResponse.<Void>builder().result(null)
                .message("delete all favourited book by user")
                .build();
    }

    @Operation(
            method = "DELETE",
            summary = "Remove all favourites by book ID",
            security = {
        @SecurityRequirement(name = "BearToken", scopes = {
                 "ADMIN"
        })
    }
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "2006", description = "Book not existed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "1006", description = "Unauthenticated, you are not the admin")

    })
    @DeleteMapping("/remove-all-by-book/{bookId}")
    public ApiResponse<Void> removeAllFavouritesByBookId(@PathVariable String bookId) {
        service.removeAllFavouriteBookByBookId(bookId);
        return ApiResponse.<Void>builder().result(null).build();
    }
}
