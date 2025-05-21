package org.example.favouriteservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.favouriteservice.dto.request.BookAddRequest;
import org.example.favouriteservice.dto.request.BookRemoveRequest;
import org.example.favouriteservice.dto.response.BookAddResponse;
import org.example.favouriteservice.dto.response.BookRemoveResponse;
import org.example.favouriteservice.dto.response.FavouriteListResponse;
import org.example.favouriteservice.entity.FavouriteBook;
import org.example.favouriteservice.exception.ErrorCode;
import org.example.favouriteservice.exception.ServiceException;
import org.example.favouriteservice.mapper.FavouriteBookMapper;
import org.example.favouriteservice.repository.FavoriteBookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FavouriteService {
    FavoriteBookRepository favoriteBookRepository;
    FavouriteBookMapper mapper;

    @PreAuthorize("authentication.name.equals(#request.username)")
    public BookAddResponse addBookToFavouriteList(@P("request")BookAddRequest request){
       if(favoriteBookRepository.existsByUsernameAndBookId(request.getUsername(), request.getBookId())){
           throw new ServiceException(ErrorCode.BOOk_EXISTED);
       }
       var book = mapper.toFavouriteBook(request);
       var response = mapper.toBookAddResponse(favoriteBookRepository.save(book));
       response.setAddSuccess(true);
       return response;
    }

    @PreAuthorize("authentication.name.equals(#request.username)")
    public BookRemoveResponse removeBookFromFavouriteList(@P("request")BookRemoveRequest request){
       FavouriteBook book = favoriteBookRepository.findByUsernameAndBookId(request.getUsername(), request.getBookId()).orElseThrow(()
       -> new ServiceException(ErrorCode.FAVOURITE_NOT_EXISTED));
       favoriteBookRepository.delete(book);
       return BookRemoveResponse.builder()
               .removeSuccess(true)
               .username(request.getUsername())
               .build();
    }

    @PreAuthorize("#username == authentication.name")
    public boolean isFavouriteByUser(@P("username")String username, String bookId){
        return favoriteBookRepository.existsByUsernameAndBookId(username, bookId);
    }

    @PreAuthorize("#username == authentication.name")
    public void removeAllFavouriteBookByUsername(@P("username")String username){
        if(!favoriteBookRepository.existsByUsername(username)){
            throw new ServiceException(ErrorCode.FAVOURITE_NOT_EXISTED);
        }
        favoriteBookRepository.deleteByUsername(username);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public void removeAllFavouriteBookByBookId(String bookId){
        if(!favoriteBookRepository.existsByBookId(bookId)){
            throw new ServiceException(ErrorCode.BOOK_NOT_EXISTED);
        }
        favoriteBookRepository.deleteAllByBookId(bookId);
    }
    
    public int getCountByBookId(String bookId){
        if(!favoriteBookRepository.existsByBookId(bookId)){
            throw new ServiceException(ErrorCode.BOOK_NOT_EXISTED);
        }
        return favoriteBookRepository.countAllByBookId(bookId);
    }

    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public List<FavouriteListResponse> getFavouriteListByUsername(@P("username")String username){
        if(!favoriteBookRepository.existsByUsername(username)){
            throw new ServiceException(ErrorCode.USER_NOT_EXISTED);
        }
        var list = favoriteBookRepository.findByUsernameOrderByFavouriteAtDesc(username);

        return list.stream().map(mapper::toFavouriteListResponse).collect(Collectors.toList());

    }
}
