package org.example.favouriteservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookAddRequest {
    String username;
    String bookId;
    LocalDate addDate;
}
