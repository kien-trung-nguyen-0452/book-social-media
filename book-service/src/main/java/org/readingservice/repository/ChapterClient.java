package org.readingservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "chapter-service")
public interface ChapterClient {

    @DeleteMapping("chapter/internal/delete/book/{bookId}")
    void deleteChaptersByBookId(@PathVariable("bookId") String bookId);
}
