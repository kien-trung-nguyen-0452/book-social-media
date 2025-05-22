package org.example.favouriteservice.repository;

import org.example.favouriteservice.entity.FavouriteBook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteBookRepository extends MongoRepository<FavouriteBook, String> {
    boolean existsByUsername(String username);

    boolean existsByBookId(String bookId);

    Optional<FavouriteBook> findByUsernameAndBookId(String username, String bookId);

    boolean existsByUsernameAndBookId(String username, String bookId);

    List<FavouriteBook> findByUsernameOrderByFavouriteAtDesc(String username);

    int countAllByBookId(String bookId);

    void deleteAllByBookId(String bookId);

    void deleteByUsername(String username);
}
