package org.readingservice.repository.httpClient.dto.wishlist;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WishlistResponse {
    private Long id;              // ID của mục wishlist
    private Long userId;          // ID người dùng
    private Long bookId;          // ID cuốn sách
    private LocalDateTime addedAt; // Thời gian thêm vào wishlist
}
