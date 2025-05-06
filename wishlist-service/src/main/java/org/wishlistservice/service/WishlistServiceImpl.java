package org.wishlistservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wishlistservice.dto.request.WishlistRequest;
import org.wishlistservice.dto.response.WishlistResponse;
import org.wishlistservice.entity.Wishlist;
import org.wishlistservice.exception.CustomException;
import org.wishlistservice.exception.ErrorCode;
import org.wishlistservice.mapper.WishlistMapper;
import org.wishlistservice.repository.WishlistRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository repository;
    private final WishlistMapper mapper;

    @Override
    public WishlistResponse addToWishlist(WishlistRequest request) {
        boolean exists = repository.existsByUserIdAndBookId(request.getUserId(), request.getBookId());
        if (exists) {
            throw new CustomException(ErrorCode.BOOK_ALREADY_EXISTS);
        }
        Wishlist entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void removeFromWishlist(Long userId, Long bookId) {
        Wishlist wishlist = repository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.WISHLIST_NOT_FOUND));
        repository.delete(wishlist);
    }

    @Override
    public List<WishlistResponse> getWishlistByUser(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
