package org.example.crawlservice.mapper;

import org.example.crawlservice.dto.data.BookInfo;
import org.example.crawlservice.dto.data.Chapter;
import org.example.crawlservice.dto.request.BookRequest;
import org.example.crawlservice.dto.request.ChapterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InternalMapper {

    @Mapping(target = "subtitle", constant = "null")
    @Mapping(target = "categoryId", constant = "0L")
    @Mapping(target = "isCompleted", constant = "false")
    @Mapping(source = "image_url", target = "coverUrl")
    BookRequest toBookRequest(BookInfo bookInfo);

    @Mapping(target = "bookId", ignore = true)        // bỏ qua
    @Mapping(target = "content", ignore = true)       // bỏ qua
    @Mapping(source = "chapter", target = "chapterNumber", qualifiedByName = "parseChapterNumber")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "images", target = "imageUrls")
    ChapterRequest toChapterRequest(Chapter chapter);

    @Named("parseChapterNumber")
    default Integer parseChapterNumber(String chapterStr) {
        try {
            return Integer.parseInt(chapterStr.replaceAll("[^\\d]", ""));
        } catch (Exception e) {
            return 0;
        }
    }
}
