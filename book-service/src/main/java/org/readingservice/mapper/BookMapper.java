package org.readingservice.mapper;

import org.readingservice.dto.request.BookCreationRequest;
import org.readingservice.dto.request.BookRequest;

import org.readingservice.dto.response.BookCreationResponse;
import org.readingservice.entity.Book;
import org.readingservice.dto.response.BookResponse;
import org.mapstruct.*;
import org.readingservice.event.BookEvent;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)  // vì bạn set id thủ công
    @Mapping(target = "viewCount", ignore = true)  // set thủ công trong service
    @Mapping(target = "categories", source = "categories")
    Book toEntity(BookRequest request);
    @Mapping(target = "categories", source = "categories")
    Book toEntity(BookCreationRequest request);
    BookResponse toResponse(Book book);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "coverUrl", target = "coverUrl")
    @Mapping(source = "chapterCount", target = "chapterCount")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "categories", target = "categories")
    BookEvent toBookEvent(Book book);


    List<BookResponse> toResponseList(List<Book> books);

    BookCreationResponse toBookCreationResponse(Book book);
    default BookEvent toBookDeletionEvent(Book book) {
        if (book == null) return null;
        return BookEvent.builder()
                .id(book.getId())
                .categories(book.getCategories())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
