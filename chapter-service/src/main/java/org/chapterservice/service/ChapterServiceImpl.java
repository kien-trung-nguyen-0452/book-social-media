package org.chapterservice.service;


import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.chapterservice.dto.request.ChapterRequest;
import org.chapterservice.dto.request.HistoryRecordRequest;
import org.chapterservice.dto.response.ChapterResponse;
import org.chapterservice.entity.Chapter;
import org.chapterservice.event.ChapterCountEvent;
import org.chapterservice.exception.ErrorCode;
import org.chapterservice.exception.ServiceException;
import org.chapterservice.mapper.ChapterMapper;
import org.chapterservice.repository.ChapterRepository;
import org.chapterservice.repository.ReadingHistoryClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.chapterservice.repository.BookServiceClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class ChapterServiceImpl implements ChapterService {

    ChapterRepository chapterRepository;
    ChapterMapper chapterMapper;
    ReadingHistoryClient readingHistoryClient;
    private final ChapterKafkaProducerService chapterKafkaProducerService;
    private final BookServiceClient bookServiceClient;

    @Override
    public ChapterResponse createChapter(String bookId, ChapterRequest request) {
        try {

            Boolean bookExists = bookServiceClient.checkBookExists(bookId);
            if (Boolean.FALSE.equals(bookExists)) {
                throw new ServiceException(ErrorCode.BOOK_NOT_FOUND); // định nghĩa thêm error code nếu chưa có
            }


            boolean isDuplicate = chapterRepository.existsByBookIdAndTitleAndChapterNumber(
                    bookId,
                    request.getTitle(),
                    request.getChapterNumber()
            );
            if (isDuplicate) {
                throw new ServiceException(ErrorCode.DUPLICATE_CHAPTER);
            }

            Chapter chapter = chapterMapper.toEntity(request);
            LocalDateTime now = LocalDateTime.now();
            chapter.setCreatedAt(now);
            chapter.setUpdatedAt(now);
            chapter.setBookId(bookId);
            chapter.setTitle(request.getTitle());
            chapter.setChapterNumber(request.getChapterNumber());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            chapter.setCreatedBy(username);
            chapter.setUpdatedBy(username);

            Chapter saved = chapterRepository.save(chapter);
            long chapterCount = chapterRepository.countByBookId(saved.getBookId());

            // Gửi event Kafka
            ChapterCountEvent event = new ChapterCountEvent(saved.getBookId(), (int) chapterCount);
            chapterKafkaProducerService.sendChapterCountEvent(event);

            return chapterMapper.toResponse(saved);

        }  catch (FeignException.NotFound ex) {
            throw new ServiceException(ErrorCode.BOOK_NOT_FOUND);
        } catch (FeignException ex) {
            throw new ServiceException(ErrorCode.INTERNAL_ERROR); // hoặc một mã lỗi phù hợp
        }
    }

    @Override
    public ChapterResponse getChapterById(String id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.CHAPTER_NOT_FOUND));

        ChapterResponse response = chapterMapper.toResponse(chapter);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            String userId = authentication.getName();


            try {
               var historyRequest = HistoryRecordRequest.builder().chapterId(chapter.getId())
                       .userId(userId)
                       .lastReadAt(LocalDate.now())
                       .bookId(chapter.getBookId())
                       .chapterId(chapter.getId())
                       .build();

               var saveResult = readingHistoryClient.saveRecord(historyRequest);
               log.info("save result: {}", saveResult.getData());
            } catch (Exception e) {
                log.warn("can not save reading record: {}", e.getMessage());
            }
        }

        return response;
    }


    @Override
    public List<ChapterResponse> getChaptersByBookId(String bookId) {
        if (chapterRepository.existsChaptersByBookId(bookId)){
            throw new ServiceException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        var chapList = chapterRepository.findByBookIdOrderByChapterNumberAsc(bookId);
        return chapList.stream().map(chapterMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ChapterResponse updateChapter(String id, ChapterRequest request) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.CHAPTER_NOT_FOUND));

        chapter.setTitle(request.getTitle());
        chapter.setContent(request.getContent());
        chapter.setChapterNumber(request.getChapterNumber());

        chapter.setBookId(request.getBookId());
        chapter.setImages(request.getImages());

        chapter.setUpdatedAt(LocalDateTime.now());


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        System.out.println("Current username: " + username);
        chapter.setUpdatedBy(username);

        return chapterMapper.toResponse(chapterRepository.save(chapter));
    }
    public String getBookIdByChapterId(String chapterId) {
        return chapterRepository.findById(chapterId)
                .map(Chapter::getBookId)
                .orElseThrow(() -> new ServiceException(ErrorCode.CHAPTER_NOT_FOUND));
    }

    @Override
    public void deleteChapter(String id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.CHAPTER_NOT_FOUND));

        String bookId = chapter.getBookId(); // 👈 Lấy bookId trước khi xóa

        chapterRepository.deleteById(id);    // ❌ Nếu làm trước thì mất dữ liệu

        ChapterCountEvent event = new ChapterCountEvent();
        event.setBookId(bookId);
        event.setCount(-1); // giảm 1 chương

        chapterKafkaProducerService.sendChapterCountEvent(event);
    }



    @Override
    public ChapterResponse getLastChapterByBookId(String bookId) {
        return chapterRepository.findFirstByBookIdOrderByChapterNumberDesc(bookId)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("No chapters found for book ID: " + bookId));
    }

    @Override
    public ChapterResponse getChapterByBookIdAndNumber(String bookId, int chapterNumber) {
        return chapterRepository.findByBookIdAndChapterNumber(bookId, chapterNumber)
                .map(chapterMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Chapter " + chapterNumber + " not found for book ID: " + bookId));
    }

    @Override
    public long countChaptersByBookId(String bookId) {

        return chapterRepository.countByBookId(bookId);
    }

    @Override
    public void deleteChaptersByBookId(String bookId) {

        chapterRepository.deleteByBookId(bookId);
    }
}
