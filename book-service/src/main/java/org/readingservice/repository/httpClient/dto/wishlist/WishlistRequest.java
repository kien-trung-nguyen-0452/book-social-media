package org.readingservice.repository.httpClient.dto.wishlist;

import lombok.Data;

@Data
public class WishlistRequest {
    private Long userId; // ID người dùng
    private Long bookId; // ID cuốn sách
}
