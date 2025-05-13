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
import java.util.Collection;
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

        BookRequest bookRequest = internalMapper.toBookRequest(info);
        BookResponse bookResponse = bookClient.createBook(bookRequest).getData();
        String bookId = String.valueOf(bookResponse.getId());

        for (Chapter chapter : inputChapters) {
            ChapterRequest chapterRequest = internalMapper.toChapterRequest(chapter);
            chapterRequest.setBookId(bookId);
            chapterRequest.setContent(chapter.getTitle());


            ChapterResponse chapterResponse = chapterClient.createChapter(chapterRequest).getData();

            List<String> newImageUrls = new ArrayList<>();
            for (String imageUrl : chapter.getImages()) {
                UploadRequest uploadRequest = UploadRequest.builder()
                        .bookId(bookId)
                        .chapterId(chapterResponse.getId())
                        .name(chapterResponse.getTitle())
                        .url(imageUrl)
                        .build();
log.info("Uploading image: {}", uploadRequest.getUrl());
                UploadResponse uploadResponse = uploadClient.uploadFromUrl(uploadRequest);
                String uploadedUrl = uploadResponse.getUrl();

                newImageUrls.add(uploadedUrl);
                log.info("Uploaded image: {}", uploadedUrl);
            }

            ChapterRequest updatedChapterRequest = ChapterRequest.builder()

                    .title(chapterResponse.getTitle())
                    .content(chapterResponse.getContent())
                    .chapterNumber(chapterRequest.getChapterNumber())
                    .bookId(bookId)
                    .imageUrls(newImageUrls.stream().toList())
                    .build();

            log.info("Updated chapter request: {}", updatedChapterRequest.getImageUrls());


            ChapterResponse updatedResponse = chapterClient.createChapter(updatedChapterRequest).getData();
            log.info("Updated chapter: {}", updatedResponse.getImageUrls());

            outputChapters.add(Chapter.builder()
                    .id(updatedResponse.getId())
                    .title(updatedResponse.getTitle())
                    .chapter(String.valueOf(updatedResponse.getChapterNumber()))
                    .images(updatedResponse.getImageUrls())
                    .build());
        }

        return CrawlResponse.builder()
                .info(info)
                .chapters(outputChapters)
                .success(true)
                .build();
    }
}
