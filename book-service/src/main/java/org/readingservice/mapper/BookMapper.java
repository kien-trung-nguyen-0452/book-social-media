package org.readingservice.mapper;

import jdk.jfr.Category;
import org.readingservice.dto.request.BookRequest;

import org.readingservice.entity.Book;
import org.readingservice.dto.response.BookResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookRequest request);
    @Mapping(source = "id", target = "id")

    BookResponse toResponse(Book book);
}
