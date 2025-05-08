package org.readingservice.repository.httpClient;

import org.readingservice.repository.httpClient.dto.wishlist.WishlistRequest;
import org.readingservice.repository.httpClient.dto.wishlist.WishlistResponse;
import org.readingservice.dto.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "wishlist-service")
public interface WishlistServiceClient {


    @PostMapping("/wishlist")
    ApiResponse<WishlistResponse> addToWishlist(@RequestBody WishlistRequest request);


    @DeleteMapping("/wishlist/{userId}/{bookId}")
    ApiResponse<Void> removeFromWishlist(
            @PathVariable("userId") Long userId,
            @PathVariable("bookId") Long bookId
    );

    @GetMapping("/wishlist/user/{userId}")
    ApiResponse<List<WishlistResponse>> getWishlistByUser(@PathVariable("userId") Long userId);
}

