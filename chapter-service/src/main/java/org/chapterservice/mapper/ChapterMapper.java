package org.chapterservice.mapper;

import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")

public interface ChapterMapper {
    @Mapping(source = "images",target = "images")
    @Mapping(target = "id", ignore = true)

    Chapter toEntity(ChapterRequest request);

    @Mapping(source = "chapter", target = "chapter")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "updatedBy", target = "updatedBy") // sửa lại thành updatedBy
    @Mapping(source = "images", target = "images")
    ChapterResponse toResponse(Chapter chapter);
}

