package org.readingservice.mapper;

import org.readingservice.dto.request.BookRequest;

import org.readingservice.entity.Book;
import org.readingservice.dto.response.BookResponse;
import org.mapstruct.*;
import org.readingservice.event.BookEvent;


@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)  // vì bạn set id thủ công
    @Mapping(target = "viewCount", ignore = true)  // set thủ công trong service
    @Mapping(target = "averageRating", ignore = true) // set thủ công
    @Mapping(target = "createdAt", ignore = true)    // set thủ công
    @Mapping(target = "updatedAt", ignore = true)    // set thủ công
    Book toEntity(BookRequest request);

    @Mapping(source = "completed", target = "isCompleted")  // nếu tên khác bạn cần map rõ
    BookResponse toResponse(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "categories", ignore = true)
    BookEvent toBookEvent(BookRequest request);
    @Mapping(source = "completed", target = "isCompleted")
    BookEvent toBookEvent(Book book);
}
