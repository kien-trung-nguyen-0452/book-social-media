package org.analyticsservice.mapper;

import org.analyticsservice.dto.request.ReadingLogRequest;
import org.analyticsservice.dto.response.ReadingLogResponse;
import org.analyticsservice.entity.ReadingLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReadingLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    ReadingLog toEntity(ReadingLogRequest request);

    ReadingLogResponse toResponse(ReadingLog entity);
}
