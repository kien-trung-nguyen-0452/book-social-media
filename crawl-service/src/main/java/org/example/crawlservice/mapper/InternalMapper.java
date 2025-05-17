package org.example.crawlservice.mapper;

import org.example.crawlservice.dto.data.BookInfo;
import org.example.crawlservice.dto.data.Chapter;
import org.example.crawlservice.dto.request.BookRequest;
import org.example.crawlservice.dto.request.ChapterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InternalMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "author", target = "author")
    @Mapping(target = "categoryId", expression = "java(org.example.crawlservice.mapper.MapperHelper.defaultCategoryId())")
    @Mapping(source = "coverUrl", target = "coverUrl")
    BookRequest toBookRequest(BookInfo bookInfo);

    @Mapping(target = "bookId", ignore = true)
    @Mapping(target = "content", ignore = true)
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
