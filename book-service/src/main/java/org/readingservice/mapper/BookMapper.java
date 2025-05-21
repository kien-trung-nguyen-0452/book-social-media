package org.readingservice.mapper;

import org.readingservice.dto.request.BookCreationRequest;
import org.readingservice.dto.request.BookRequest;

import org.readingservice.dto.response.BookCreationResponse;
import org.readingservice.entity.Book;
import org.readingservice.dto.response.BookResponse;
import org.mapstruct.*;
import org.readingservice.event.BookEvent;


@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)  // vì bạn set id thủ công
    @Mapping(target = "viewCount", ignore = true)  // set thủ công trong service



    Book toEntity(BookRequest request);
    Book toEntity(BookCreationRequest request);
    BookResponse toResponse(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "categories", ignore = true)
    BookEvent toBookEvent(BookRequest request);
    @Mapping(source = "isCompleted", target = "isCompleted")
    BookEvent toBookEvent(Book book);

    BookCreationResponse toBookCreationResponse(Book book);
}
