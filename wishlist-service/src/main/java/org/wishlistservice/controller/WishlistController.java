package org.wishlistservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.wishlistservice.dto.common.ApiResponse;
import org.wishlistservice.dto.request.WishlistRequest;
import org.wishlistservice.dto.response.WishlistResponse;
import org.wishlistservice.service.WishlistService;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ApiResponse<WishlistResponse> addToWishlist(@RequestBody WishlistRequest request) {
        WishlistResponse response = wishlistService.addToWishlist(request);
        return ApiResponse.<WishlistResponse>builder()
                .data(response)  // Sử dụng data thay vì result
                .message("Book added to wishlist")
                .status(201)  // HttpStatus.CREATED.value()
                .build();
    }

    @DeleteMapping("/{userId}/{bookId}")
    public ApiResponse<Void> removeFromWishlist(@PathVariable Long userId, @PathVariable Long bookId) {
        wishlistService.removeFromWishlist(userId, bookId);
        return ApiResponse.<Void>builder()
                .data(null)  // Sử dụng data thay vì result
                .message("Book removed from wishlist")
                .status(200)  // HttpStatus.OK.value()
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<WishlistResponse>> getWishlist(@PathVariable Long userId) {
        List<WishlistResponse> wishlist = wishlistService.getWishlistByUser(userId);
        return ApiResponse.<List<WishlistResponse>>builder()
                .data(wishlist)  // Sử dụng data thay vì result
                .message("Wishlist fetched successfully")
                .status(200)  // HttpStatus.OK.value()
                .build();
    }
}
