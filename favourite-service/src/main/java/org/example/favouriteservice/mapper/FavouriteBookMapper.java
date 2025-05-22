package org.example.favouriteservice.mapper;

import org.example.favouriteservice.dto.request.BookAddRequest;
import org.example.favouriteservice.dto.response.BookAddResponse;
import org.example.favouriteservice.dto.response.FavouriteListResponse;
import org.example.favouriteservice.entity.FavouriteBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavouriteBookMapper {
    @Mapping(target = "id", ignore = true)
    FavouriteBook toFavouriteBook(BookAddRequest request);
    BookAddResponse toBookAddResponse(FavouriteBook book);
    FavouriteListResponse toFavouriteListResponse(FavouriteBook book);
}
