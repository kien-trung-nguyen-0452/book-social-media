package org.example.favouriteservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookAddResponse {
    String username;
    String bookId;
    LocalDate addDate;
    boolean addSuccess;
}
