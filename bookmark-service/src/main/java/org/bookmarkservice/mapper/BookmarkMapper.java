package org.bookmarkservice.mapper;

import org.bookmarkservice.dto.request.BookmarkRequest;
import org.bookmarkservice.dto.response.BookmarkResponse;
import org.bookmarkservice.entity.Bookmark;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {
    Bookmark toEntity(BookmarkRequest request);
    BookmarkResponse toResponse(Bookmark bookmark);
}
