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
import org.example.crawlservice.exception.ErrorCode;
import org.example.crawlservice.exception.ServiceException;
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

    private boolean isValidUrl(String url) {
        try {
            new java.net.URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public CrawlResponse crawl(Metadata metadata) {
        BookInfo info = metadata.getInfo();
        List<Chapter> inputChapters = metadata.getChapters();

        // Kiểm tra tính hợp lệ của tất cả URL ảnh
        for (Chapter chapter : inputChapters) {
            if (chapter.getImages() != null) {
                for (String imageUrl : chapter.getImages()) {
                    if (!isValidUrl(imageUrl)) {
                        log.error("Invalid image URL: {}", imageUrl);
                        throw new ServiceException(ErrorCode.URL_INVALID);
                    }
                }
            }
        }

        // Tạo sách
        BookRequest bookRequest = internalMapper.toBookRequest(info);
        BookResponse bookResponse = bookClient.createBook(bookRequest).getData();
        String bookId = String.valueOf(bookResponse.getId());

        List<String> createdChapterIds = new ArrayList<>();
        List<Chapter> outputChapters = new ArrayList<>();

        try {
            for (Chapter chapter : inputChapters) {
                // Bước 1: Tạo chapter trước
                ChapterRequest chapterRequest = internalMapper.toChapterRequest(chapter);
                chapterRequest.setBookId(bookId);
                chapterRequest.setTitle(chapter.getTitle());
                ChapterResponse chapterResponse = chapterClient.createChapter(bookId, chapterRequest).getData();
                String chapterId = chapterResponse.getId();
                createdChapterIds.add(chapterId);

                // Bước 2: Upload ảnh
                UploadRequest uploadRequest = UploadRequest.builder()
                        .bookId(bookId)
                        .chapterId(chapterId)
                        .name(chapter.getTitle())
                        .url(chapter.getImages())
                        .build();

                UploadResponse uploadResponse = uploadClient.uploadFromUrl(uploadRequest).getData();
                List<String> uploadedUrls = uploadResponse.getUrl();

                // Bước 3: Cập nhật lại chapter với link ảnh mới
                ChapterRequest updateRequest = ChapterRequest.builder()
                        .bookId(bookId)
                        .title(chapter.getTitle())
                        .chapterNumber(chapterRequest.getChapterNumber())
                        .content(chapterRequest.getContent())
                        .images(uploadedUrls)
                        .build();

                chapterClient.updateChapter(chapterId, updateRequest); // Bạn cần đảm bảo API này tồn tại

                // Bước 4: Lưu thông tin chapter để trả về
                outputChapters.add(Chapter.builder()
                        .id(chapterId)
                        .title(chapter.getTitle())
                        .chapterNumber(chapter.getChapterNumber())
                        .images(uploadedUrls)
                        .build());
            }

            return CrawlResponse.builder()
                    .info(info)
                    .chapters(outputChapters)
                    .success(true)
                    .build();

        } catch (Exception e) {
            log.error("Error during crawl, starting compensation cleanup", e);

            // Rollback các chapter đã tạo
            for (String chapterId : createdChapterIds) {
                try {
                    chapterClient.deleteChapter(chapterId);
                } catch (Exception ex) {
                    log.error("Failed to delete chapter id {} during compensation", chapterId, ex);
                }
            }

            // Rollback sách đã tạo
            try {
                bookClient.deleteBook(bookId);
            } catch (Exception ex) {
                log.error("Failed to delete book id {} during compensation", bookId, ex);
            }

            throw new ServiceException(ErrorCode.URL_INVALID);
        }
    }

}
