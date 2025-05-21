package org.example.favouriteservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("FavouriteBook")
@Data
public class FavouriteBook {
    @Id
    String id;
    String bookId;
    String username;
    LocalDate favouriteAt;
}
