//package org.readingservice.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.readingservice.controler.BookController;
//import org.readingservice.dto.request.BookRequest;
//import org.readingservice.dto.response.BookResponse;
//import org.readingservice.repository.httpClient.dto.chapter.ChapterResponse;
//import org.readingservice.service.BookService;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class BookControllerTest {
//
//    private MockMvc mockMvc;
//    private BookService bookService;
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        bookService = org.mockito.Mockito.mock(BookService.class);
//        BookController bookController = new BookController(bookService);
//        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    public void testCreateBook() throws Exception {
//        BookRequest request = BookRequest.builder()
//                .title("My Book")
//                .subtitle("Subtitle")
//                .description("Some description")
//                .author("Author")
//                .coverUrl("http://image.com")
//                .isCompleted(true)
//                .categories(List.of("Fantasy"))
//                .build();
//
//        BookResponse response = BookResponse.builder()
//
//                .title("My Book")
//                .subtitle("Subtitle")
//                .description("Some description")
//                .author("Author")
//                .coverUrl("http://image.com")
//                .isCompleted(true)
//                .categories(List.of("Fantasy"))
//                .chapterCount(0)
//
//                .averageRating(0.0)
//                .build();
//
//        when(bookService.createBook(any(BookRequest.class))).thenReturn(response);
//
//        mockMvc.perform(post("/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("My Book"));
//    }
//
//    @Test
//    public void testGetAllBooks() throws Exception {
//        when(bookService.getAllBooks()).thenReturn(List.of(BookResponse.builder().id( n1L).title("Test").build()));
//
//        mockMvc.perform(get("/books"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].id").value(1));
//    }
//
//    @Test
//    public void testGetBookById() throws Exception {
//        when(bookService.getBookById(1L)).thenReturn(BookResponse.builder().id(1L).title("Book 1").build());
//
//        mockMvc.perform(get("/books/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("Book 1"));
//    }
//
//    @Test
//    public void testUpdateBook() throws Exception {
//        BookRequest request = BookRequest.builder().title("Updated Title").build();
//        BookResponse response = BookResponse.builder().id(1L).title("Updated Title").build();
//
//        when(bookService.updateBook(eq(1L), any(BookRequest.class))).thenReturn(response);
//
//        mockMvc.perform(put("/books/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("Updated Title"));
//    }
//
//    @Test
//    public void testDeleteBook() throws Exception {
//        doNothing().when(bookService).deleteBook(1L);
//
//        mockMvc.perform(delete("/books/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Book deleted successfully"));
//    }
//
//    @Test
//    public void testGetBooksByAuthor() throws Exception {
//        when(bookService.getBooksByAuthor("Author")).thenReturn(List.of(BookResponse.builder().title("By Author").build()));
//
//        mockMvc.perform(get("/books/author").param("author", "Author"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].title").value("By Author"));
//    }
//
//    @Test
//    public void testGetBooksByCategory() throws Exception {
//        when(bookService.getBooksByCategory("Fantasy")).thenReturn(List.of(BookResponse.builder().title("Fantasy Book").build()));
//
//        mockMvc.perform(get("/books/category/Fantasy"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].title").value("Fantasy Book"));
//    }
//
//    @Test
//    public void testSearchBooks() throws Exception {
//        when(bookService.searchBooks("keyword")).thenReturn(List.of(BookResponse.builder().title("Result Book").build()));
//
//        mockMvc.perform(get("/books/search").param("keyword", "keyword"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].title").value("Result Book"));
//    }
//
//    @Test
//    public void testGetTopRatedBooks() throws Exception {
//        when(bookService.getTopRatedBooks(5)).thenReturn(List.of(BookResponse.builder().title("Top Book").build()));
//
//        mockMvc.perform(get("/books/top-rated").param("limit", "5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].title").value("Top Book"));
//    }
//
//    @Test
//    public void testGetChaptersByBookId() throws Exception {
//        when(bookService.getChaptersByBookId(1L)).thenReturn(List.of(ChapterResponse.builder().chapterNumber(1).build()));
//
//        mockMvc.perform(get("/books/1/chapters"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].chapterNumber").value(1));
//    }
//
//    @Test
//    public void testGetLastChapter() throws Exception {
//        when(bookService.getLastChapter(1L)).thenReturn(ChapterResponse.builder().chapterNumber(10).build());
//
//        mockMvc.perform(get("/books/1/chapters/last"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.chapterNumber").value(10));
//    }
//
//    @Test
//    public void testGetChapterById() throws Exception {
//        when(bookService.getChapterById("abc123")).thenReturn(ChapterResponse.builder().title("Chapter A").build());
//
//        mockMvc.perform(get("/books/chapters/abc123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("Chapter A"));
//    }
//
//    @Test
//    public void testGetChapterByBookIdAndNumber() throws Exception {
//        when(bookService.getChapterByBookIdAndNumber(1L, 2)).thenReturn(ChapterResponse.builder().title("Chapter 2").build());
//
//        mockMvc.perform(get("/books/1/chapters/number/2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("Chapter 2"));
//    }
//}
