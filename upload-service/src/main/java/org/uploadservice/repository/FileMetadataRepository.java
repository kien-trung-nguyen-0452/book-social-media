package org.uploadservice.repository;

import org.uploadservice.entity.FileMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface FileMetadataRepository extends MongoRepository<FileMetadata, String> {
    Optional<FileMetadata> findByFileName(String fileName);
}
