package org.readingservice.client;

import org.readingservice.client.dto.wishlist.WishlistRequest;
import org.readingservice.client.dto.wishlist.WishlistResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "wishlist-service", url = "${service.wishlist.base-url}")
public interface WishlistServiceClient {


    @PostMapping("/api/wishlist")
    ApiResponse<WishlistResponse> addToWishlist(@RequestBody WishlistRequest request);


    @DeleteMapping("/api/wishlist/{userId}/{bookId}")
    ApiResponse<Void> removeFromWishlist(
            @PathVariable("userId") Long userId,
            @PathVariable("bookId") Long bookId
    );

    @GetMapping("/api/wishlist/user/{userId}")
    ApiResponse<List<WishlistResponse>> getWishlistByUser(@PathVariable("userId") Long userId);
}

