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

        // Lưu các chapterId đã tạo để có thể xóa khi lỗi
        List<String> createdChapterIds = new ArrayList<>();

        try {
            List<Chapter> outputChapters = new ArrayList<>();

            // Duyệt từng chapter
            // ...

            for (Chapter chapter : inputChapters) {
                ChapterRequest chapterRequest = internalMapper.toChapterRequest(chapter);
                chapterRequest.setBookId(bookId);
                chapterRequest.setTitle(chapter.getTitle());

                ChapterResponse chapterResponse = chapterClient.createChapter(bookId,chapterRequest).getData();
                String chapterId = chapterResponse.getId();
                createdChapterIds.add(chapterId);

                // Gửi toàn bộ ảnh trong chapter một lần
                UploadRequest uploadRequest = UploadRequest.builder()
                        .bookId(bookId)
                        .chapterId(chapterId)
                        .name(chapter.getTitle())
                        .url(chapter.getImages())  // truyền List<String> thay vì từng String
                        .build();

                ApiResponse<UploadResponse> uplresponse = uploadClient.uploadFromUrl(uploadRequest);
                UploadResponse uploadResponse = uplresponse.getData();

                outputChapters.add(Chapter.builder()
                        .id(chapterId)
                        .title(chapterResponse.getTitle())
                        .chapter(String.valueOf(chapterResponse.getChapterNumber()))
                        .images(uploadResponse.getUrl()) // lấy list ảnh mới upload
                        .build());
            }


            return CrawlResponse.builder()
                    .info(info)
                    .chapters(outputChapters)
                    .success(true)
                    .build();

        } catch (Exception e) {
            log.error("Error during crawl, starting compensation cleanup", e);

            // Xóa từng chapter đã tạo
            for (String chapterId : createdChapterIds) {
                try {
                    chapterClient.deleteChapter(chapterId);
                } catch (Exception ex) {
                    log.error("Failed to delete chapter id {} during compensation", chapterId, ex);
                }
            }

            // Xóa sách đã tạo
            try {
                bookClient.deleteBook(bookId);
            } catch (Exception ex) {
                log.error("Failed to delete book id {} during compensation", bookId, ex);
            }

            // Ném lại exception để báo lỗi
            throw new ServiceException(ErrorCode.URL_INVALID);
        }
    }
}
