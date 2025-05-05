package org.wishlistservice.service;

import org.wishlistservice.dto.request.WishlistRequest;
import org.wishlistservice.dto.response.WishlistResponse;

import java.util.List;

public interface WishlistService {
    WishlistResponse addToWishlist(WishlistRequest request);
    void removeFromWishlist(Long userId, Long bookId);
    List<WishlistResponse> getWishlistByUser(Long userId);
}
