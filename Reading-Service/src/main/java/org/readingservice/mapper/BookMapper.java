package org.readingservice.mapper;

import org.readingservice.dto.request.BookRequest;

import org.readingservice.entity.Book;
import org.readingservice.dto.response.BookResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookRequest request);
    BookResponse toBookResponse(Book book);
    BookResponse toResponse(Book book);
}