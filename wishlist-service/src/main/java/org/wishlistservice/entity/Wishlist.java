package org.wishlistservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishlists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wishlist {
    @Id
    private String id;
    private Long userId;
    private Long bookId;
    private String addedAt; // Hoặc LocalDateTime nếu cần thời gian chính xác
}

