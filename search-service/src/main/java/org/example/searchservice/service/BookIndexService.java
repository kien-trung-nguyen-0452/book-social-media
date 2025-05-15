package org.example.searchservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.searchservice.dto.request.BookIndexDeleteRequest;
import org.example.searchservice.dto.request.BookIndexUpdateRequest;
import org.example.searchservice.dto.response.BookIndexResponse;
import org.example.searchservice.dto.response.BookSearchingResult;
import org.example.searchservice.entity.BookIndex;
import org.example.searchservice.mapper.BookIndexMapper;
import org.example.searchservice.repository.BookIndexRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookIndexService {
    BookIndexRepository bookIndexRepository;

    @Qualifier("bookIndexMapperImpl")
    BookIndexMapper mapper;

    public List<BookSearchingResult> getAll(){
        Iterable<BookIndex> result = bookIndexRepository.findAll();
        return StreamSupport.stream(result.spliterator(), false).map(mapper::toBookSearchingResult).collect(Collectors.toList());
    }
    public List<BookSearchingResult> findByTitleContaining(String keyword){
        List<BookIndex> result = bookIndexRepository.findByTitleContainingIgnoreCase(keyword);
        return result.stream().map(mapper::toBookSearchingResult).collect(Collectors.toList());
    }

    public BookIndexResponse createBookIndex(BookIndex bookIndex){
        return mapper.toBookIndexResponse(bookIndexRepository.save(bookIndex));
    }

    public BookIndexResponse updateBookIndex(BookIndexUpdateRequest bookIndexUpdateRequest){
        BookIndex bookIndex = mapper.toBookIndex(bookIndexUpdateRequest);;
        return mapper.toBookIndexResponse(bookIndexRepository.save(bookIndex));
    }

    public void deleteBookIndex(BookIndexDeleteRequest request){
        bookIndexRepository.delete(bookIndexRepository.findBookIndexById(request.getId()));
    }


}
