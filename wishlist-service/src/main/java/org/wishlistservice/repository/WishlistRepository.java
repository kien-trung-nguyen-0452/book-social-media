package org.wishlistservice.repository;

import org.wishlistservice.entity.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends MongoRepository<Wishlist, String> {
    List<Wishlist> findByUserId(Long userId);
    Optional<Wishlist> findByUserIdAndBookId(Long userId, Long bookId);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
