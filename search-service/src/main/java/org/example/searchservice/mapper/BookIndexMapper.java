package org.example.searchservice.mapper;

import org.example.searchservice.dto.request.BookIndexUpdateRequest;
import org.example.searchservice.dto.response.BookIndexResponse;
import org.example.searchservice.dto.response.BookSearchingResult;
import org.example.searchservice.entity.BookIndex;
import org.example.searchservice.event.BookEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookIndexMapper {
    BookSearchingResult toBookSearchingResult(BookIndex bookIndex);

    BookIndexResponse toBookIndexResponse(BookIndex bookIndex);

    BookIndex toBookIndex(BookEvent event);

    BookIndex toBookIndex(BookIndexUpdateRequest request);
}
