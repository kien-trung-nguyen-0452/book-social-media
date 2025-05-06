package org.wishlistservice.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistResponse {
    private String id;
    private Long userId;
    private Long bookId;
    private String addedAt;
}
