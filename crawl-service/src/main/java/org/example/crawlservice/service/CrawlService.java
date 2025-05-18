package org.example.crawlservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.crawlservice.dto.data.BookInfo;
import org.example.crawlservice.dto.data.Chapter;
import org.example.crawlservice.dto.request.BookRequest;
import org.example.crawlservice.dto.request.ChapterRequest;
import org.example.crawlservice.dto.request.UploadRequest;
import org.example.crawlservice.dto.response.*;
import org.example.crawlservice.dto.data.Metadata;
import org.example.crawlservice.mapper.InternalMapper;
import org.example.crawlservice.repository.BookClient;
import org.example.crawlservice.repository.ChapterClient;
import org.example.crawlservice.repository.UploadClient;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrawlService {
    BookClient bookClient;
    ChapterClient chapterClient;
    UploadClient uploadClient;
    InternalMapper internalMapper;

    public CrawlResponse crawl(Metadata metadata) {
        BookInfo info = metadata.getInfo();
        List<Chapter> inputChapters = metadata.getChapters();
        List<Chapter> outputChapters = new ArrayList<>();

        // 1. Tạo sách, lấy bookId
        BookRequest bookRequest = internalMapper.toBookRequest(info);
        BookResponse bookResponse = bookClient.createBook(bookRequest).getData();
        String bookId = String.valueOf(bookResponse.getId());

        // 2. Duyệt qua từng chapter đầu vào
        for (Chapter chapter : inputChapters) {
            log.info("Chapter {} images: {}", chapter.getChapter(), chapter.getImages());
            ChapterRequest chapterRequest = internalMapper.toChapterRequest(chapter);
            chapterRequest.setBookId(bookId);
            chapterRequest.setTitle(chapter.getTitle());

            // 3. Tạo chapter để lấy chapterId
            ChapterResponse chapterResponse = chapterClient.createChapter(chapterRequest).getData();
            String chapterId = chapterResponse.getId();

            // 4. Upload ảnh dùng chapterId mới lấy được
            List<String> newImageUrls = new ArrayList<>();
            if (chapter.getImages() != null) {
                for (String imageUrl : chapter.getImages()) {
                    UploadRequest uploadRequest = UploadRequest.builder()
                            .bookId(bookId)
                            .chapterId(chapterId)
                            .name(chapter.getTitle())
                            .url(imageUrl)
                            .build();
                    try {
                        ApiResponse<UploadResponse> uplresponse = uploadClient.uploadFromUrl(uploadRequest);
                        UploadResponse uploadResponse = uplresponse.getData();
                        log.info("Uploaded image URL: {}", uploadResponse.getUrl());
                        newImageUrls.add(uploadResponse.getUrl());
                    } catch (Exception e) {
                        log.error("Failed to upload image from URL: {}", imageUrl, e);
                        // fallback trả về URL gốc nếu upload lỗi
                        newImageUrls.add(imageUrl);
                    }
                }
            }

            // 6. Build output chapter cho response trả về client
            outputChapters.add(Chapter.builder()
                    .id(chapterId)
                    .title(chapterResponse.getTitle())
                    .chapter(String.valueOf(chapterResponse.getChapterNumber()))
                    .images(newImageUrls)
                    .build());
        }

        return CrawlResponse.builder()
                .info(info)
                .chapters(outputChapters)
                .success(true)
                .build();
    }
}