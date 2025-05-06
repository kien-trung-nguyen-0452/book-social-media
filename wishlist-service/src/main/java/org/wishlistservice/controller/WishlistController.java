package org.wishlistservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<WishlistResponse>> addToWishlist(@RequestBody WishlistRequest request) {
        WishlistResponse response = wishlistService.addToWishlist(request);
        return new ResponseEntity<>(
                new ApiResponse<>(response, "Book added to wishlist", HttpStatus.CREATED.value()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(@PathVariable Long userId, @PathVariable Long bookId) {
        wishlistService.removeFromWishlist(userId, bookId);
        return ResponseEntity.ok(new ApiResponse<>(null, "Book removed from wishlist", HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getWishlist(@PathVariable Long userId) {
        List<WishlistResponse> wishlist = wishlistService.getWishlistByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(wishlist, "Wishlist fetched successfully", HttpStatus.OK.value()));
    }
}
