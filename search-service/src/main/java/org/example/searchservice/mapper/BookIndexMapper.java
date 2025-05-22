package org.example.searchservice.mapper;

import org.example.searchservice.dto.request.BookIndexUpdateRequest;
import org.example.searchservice.dto.response.BookIndexResponse;
import org.example.searchservice.dto.response.BookSearchingResult;
import org.example.searchservice.entity.BookIndex;
import org.readingservice.event.BookEvent;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface BookIndexMapper {
    BookSearchingResult toBookSearchingResult(BookIndex bookIndex);

    BookIndexResponse toBookIndexResponse(BookIndex bookIndex);

    default BookIndex toBookIndex(BookEvent event) {
        if (event == null) return null;

        return BookIndex.builder()
                .id(event.getId())
                .title(event.getTitle())
                .subtitle(event.getSubtitle())
                .description(event.getDescription())
                .author(event.getAuthor())
                .coverUrl(event.getCoverUrl() != null ? event.getCoverUrl() : "")
                .chapterCount(event.getChapterCount())
                .createdAt(event.getCreatedAt() != null ? event.getCreatedAt() : LocalDateTime.now())
                .updatedAt(event.getUpdatedAt() != null ? event.getUpdatedAt() : LocalDateTime.now())
                .categories(event.getCategories())
                .build();
    } ;


    BookIndex toBookIndex(BookIndexUpdateRequest request);
}
