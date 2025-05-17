package org.chapterservice.mapper;

import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ChapterMapper {

    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "bookId", target = "bookId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "chapter", target = "chapter")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "chapterNumber", target = "chapterNumber")
    @Mapping(target = "id", ignore = true)

    Chapter toEntity(ChapterRequest request);

    @Mapping(source = "chapter", target = "chapter")
    @Mapping(source = "updatedAt", target = "updatedAt")

    ChapterResponse toResponse(Chapter chapter);
}
