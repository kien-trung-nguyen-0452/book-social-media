package org.chapterservice.mapper;

import org.chapterservice.dto.ChapterRequest;
import org.chapterservice.dto.ChapterResponse;
import org.chapterservice.entity.Chapter;
import org.mapstruct.*;
@Mapper(componentModel = "spring")
public interface ChapterMapper {
    Chapter toEntity(ChapterRequest request);
    ChapterResponse toResponse(Chapter chapter);
}

