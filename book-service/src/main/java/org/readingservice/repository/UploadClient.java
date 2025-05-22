package org.readingservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "upload-service")
public interface UploadClient {

    @DeleteMapping("upload/upload/book/{bookId}")
    void deleteBook(@PathVariable String bookId);

}