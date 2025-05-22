package org.example.favouriteservice.dto.request;

import lombok.Data;

@Data
public class BookRemoveRequest {
    String username;
    String bookId;
}
