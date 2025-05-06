package org.wishlistservice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistRequest {
    private Long userId;
    private Long bookId;
}
