//package org.readingservice.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.readingservice.controler.BookController;
//import org.readingservice.dto.request.BookRequest;
//import org.readingservice.dto.response.BookResponse;
//
//import org.readingservice.service.BookService;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.*;
//
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
//
//
//}
