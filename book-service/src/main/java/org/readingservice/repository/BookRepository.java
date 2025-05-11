package org.readingservice.repository;

import org.readingservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Đã sửa từ categoryId sang category (danh sách)
    @Query("SELECT b FROM Book b JOIN b.categories c WHERE LOWER(c) = LOWER(:category)")
    List<Book> findByCategory(@Param("category") String category);

    @Query("SELECT b FROM Book b ORDER BY b.averageRating DESC")
    List<Book> findTopRatedBooks(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %:keyword% OR LOWER(b.author) LIKE %:keyword% OR LOWER(b.description) LIKE %:keyword%")
    List<Book> searchBooks(@Param("keyword") String keyword);
}
