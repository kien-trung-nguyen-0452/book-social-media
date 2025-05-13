package org.readingservice.repository;

import org.readingservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    // Tìm theo tên tác giả (không phân biệt hoa thường)
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Tìm theo category (giả sử categories là List<String>)
    List<Book> findByCategoriesIgnoreCase(String category);
    // Nếu categories là List<Category>:
    // List<Book> findByCategories_NameIgnoreCase(String categoryName);

    // Lấy sách có đánh giá cao nhất
    List<Book> findAllByOrderByAverageRatingDesc(Pageable pageable);

    // Tìm sách theo tiêu đề, tác giả, hoặc mô tả chứa keyword (không phân biệt hoa thường)
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String keyword1,
            String keyword2,
            String keyword3
    );
}
