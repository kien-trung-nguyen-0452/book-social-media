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

import org.example.crawlservice.mapper.InternalMapper;
import org.example.crawlservice.repository.BookClient;
import org.example.crawlservice.repository.ChapterClient;
import org.example.crawlservice.repository.UploadClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrawlService {
    BookClient bookClient;
    ChapterClient chapterClient;
    UploadClient uploadClient;
    InternalMapper internalMapper;


    public CrawlResponse crawl(Metadata metadata){
        BookInfo info = metadata.getInfo();
        List<Chapter> chapters = metadata.getChapters();
        BookResponse bookResponse = bookClient.createBook(info);
        long bookId = bookResponse.getId();
        for (Chapter chapter: chapters){
            ChapterRequest req = internalMapper.toChapterRequest(chapter);
            req.setBookId(bookId);
            chapterClient.createChapter(req);

        }
        return null;
    }


}
