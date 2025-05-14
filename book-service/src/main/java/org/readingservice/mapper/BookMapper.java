package org.readingservice.mapper;

import org.readingservice.dto.request.BookRequest;

import org.readingservice.entity.Book;
import org.readingservice.dto.response.BookResponse;
import org.mapstruct.*;
import org.readingservice.event.BookEvent;


@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookRequest request);
    BookResponse toResponse(Book book);

    @Mapping(target = "categories", ignore = true)
    BookEvent toBookEvent(BookRequest request);
}
