package org.example.favouriteservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavouriteCountResponse {
    int countNumber;
}
