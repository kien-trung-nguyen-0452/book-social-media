package org.readingservice.readingservice.mapper;

import org.readingservice.dto.BookDTO;
import org.readingservice.entity.Book;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    public static Book toEntity(BookDTO dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .author(dto.getAuthor())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
