package org.example.favouriteservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRemoveResponse {
    String username;
    boolean removeSuccess;
}
