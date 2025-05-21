package org.uploadservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.uploadservice.dto.response.*;
import org.uploadservice.entity.FileMetadata;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {


    @Mapping(source = "id", target = "publicId")
    FromUrlUploadResponse toFromUrlUploadResponse(FileMetadata fileMetadata);


    @Mapping(source = "bookId", target = "bookId")
    CoverUploadResponse toCoverUploadResponse(FileMetadata fileMetadata);


    @Mapping(source = "relatedId", target = "userId")
    AvatarUploadResponse toAvatarUploadResponse(FileMetadata fileMetadata);


    @Mapping(source = "id", target = "publicId")
    @Mapping(source = "category", target = "category")
    UploadImageResponse toUploadResponse(FileMetadata metadata);
}
