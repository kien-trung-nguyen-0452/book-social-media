package org.example.favouriteservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FavouriteListResponse {
    String bookId;
    String username;
    LocalDate favouriteAt;
}
