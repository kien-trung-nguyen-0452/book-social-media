package org.readingservice.client;

import org.readingservice.dto.response.ChapterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

// Chú ý: "chapter-service" phải đúng tên app bên Chapter Service đăng ký với Eureka hoặc cài trong config
@FeignClient(name = "Chapter-Service",url = "http://localhost:8080")
public interface ChapterClient {

    @GetMapping("/api/chapters/{id}")
    ChapterDTO getChapterById(@PathVariable("id") Long id);

    @GetMapping("/api/chapters/book/{bookId}")
    List<ChapterResponse> getChaptersByBookId(@PathVariable("bookId") Long bookId);
}
