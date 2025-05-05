package org.uploadservice.mapper;

import org.uploadservice.dto.response.UploadResponse;
import org.uploadservice.entity.FileMetadata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {
    UploadResponse toResponse(FileMetadata fileMetadata);
}
