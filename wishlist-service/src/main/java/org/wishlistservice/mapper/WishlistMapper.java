package org.wishlistservice.mapper;

import org.mapstruct.Mapper;

import org.wishlistservice.dto.request.WishlistRequest;
import org.wishlistservice.dto.response.WishlistResponse;
import org.wishlistservice.entity.Wishlist;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    Wishlist toEntity(WishlistRequest request);

    WishlistResponse toResponse(Wishlist wishlist);
}
