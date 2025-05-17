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

        BookRequest bookRequest = internalMapper.toBookRequest(info);
        BookResponse bookResponse = bookClient.createBook(bookRequest).getData();
        String bookId = String.valueOf(bookResponse.getId());

        for (Chapter chapter : inputChapters) {
            ChapterRequest chapterRequest = internalMapper.toChapterRequest(chapter);
            chapterRequest.setBookId(bookId);
            chapterRequest.setTitle(chapter.getTitle());

            List<String> newImageUrls = new ArrayList<>();
            if (chapter.getImages() != null) {
                for (String imageUrl : chapter.getImages()) {
                    UploadRequest uploadRequest = UploadRequest.builder()
                            .bookId(bookId)
                            .name(chapter.getTitle())
                            .url(imageUrl)
                            .build();

                    UploadResponse uploadResponse = uploadClient.uploadFromUrl(uploadRequest);
                    newImageUrls.add(uploadResponse.getUrl());
                }
            }
            chapterRequest.setImageUrls(newImageUrls);

            ChapterResponse chapterResponse = chapterClient.createChapter(chapterRequest).getData();

            outputChapters.add(Chapter.builder()
                    .id(chapterResponse.getId())
                    .title(chapterResponse.getTitle())
                    .chapter(String.valueOf(chapterResponse.getChapterNumber()))
                    .images(chapterResponse.getImageUrls())
                    .build());
        }


        return CrawlResponse.builder()
                .info(info)
                .chapters(outputChapters)
                .success(true)
                .build();
    }
}
