package org.example.crawlservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.crawlservice.dto.data.BookInfo;
import org.example.crawlservice.dto.data.Chapter;
import org.example.crawlservice.dto.request.ChapterRequest;
import org.example.crawlservice.dto.response.BookResponse;
import org.example.crawlservice.dto.response.CrawlResponse;
import org.example.crawlservice.dto.data.Metadata;
import org.example.crawlservice.dto.response.UploadResponse;
import org.example.crawlservice.mapper.InternalMapper;
import org.example.crawlservice.repository.BookClient;
import org.example.crawlservice.repository.ChapterClient;
import org.example.crawlservice.repository.UploadClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrawlService {
    BookClient bookClient;       // Giao tiếp với book-service
    ChapterClient chapterClient; // Giao tiếp với chapter-service
    UploadClient uploadClient;   // Hỗ trợ upload hình ảnh
    InternalMapper internalMapper;

    public CrawlResponse crawl(Metadata metadata) {
        BookInfo info = metadata.getInfo();
        List<Chapter> inputChapters = metadata.getChapters();

        // Kiểm tra tính hợp lệ của `Metadata`
        if (info == null || info.getTitle() == null || info.getAuthor() == null) {
            throw new IllegalArgumentException("Metadata does not contain all required BookInfo fields.");
        }

        // Tạo sách qua BookClient
        BookResponse bookResponse;
        try {
            // Gửi thông tin book đến book-service
            bookResponse = bookClient.createBook(info);
            if (bookResponse == null || bookResponse.getId() == null) {
                throw new IllegalStateException("Book creation failed. BookResponse is null or missing ID.");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create book: " + e.getMessage(), e);
        }

        // Lấy ID của sách vừa được tạo
        Long bookId = bookResponse.getId();
        boolean allSuccess = true;

        // Xử lý chapter và upload hình ảnh (nếu có)
        for (Chapter chapter : inputChapters) {
            try {
                ChapterRequest chapterRequest = internalMapper.toChapterRequest(chapter);
                chapterRequest.setBookId(bookId);

                if (chapter.getImages() != null && !chapter.getImages().isEmpty()) {
                    List<String> imageUrls = chapter.getImages().stream()
                            .map(uploadClient::upload)
                            .map(UploadResponse::getUrl)
                            .toList();
                    chapterRequest.setImageUrls(imageUrls);
                }

                // Gửi chapter tới chapter-service
                chapterClient.createChapter(chapterRequest);
            } catch (Exception e) {
                allSuccess = false;
                e.printStackTrace(); // Log lỗi chapter không thành công
            }
        }

        // Trả về phản hồi
        return CrawlResponse.builder()
                .info(info)
                .chapters(inputChapters)
                .success(allSuccess)
                .build();
    }
}