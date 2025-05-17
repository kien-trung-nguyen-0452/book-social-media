//package org.chapterservice.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.chapterservice.dto.request.ChapterRequest;
//import org.chapterservice.dto.response.ChapterResponse;
//import org.chapterservice.service.ChapterService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class ChapterControllerTest {
//
//    private MockMvc mockMvc;
//    private ChapterService chapterService;
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        chapterService = Mockito.mock(ChapterService.class);
//        ChapterController chapterController = new ChapterController(chapterService);
//        mockMvc = MockMvcBuilders.standaloneSetup(chapterController).build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    public void testCreateChapter() throws Exception {
//        ChapterRequest request = ChapterRequest.builder()
//                .chapter("1")
//                .title("Chapter One")
//                .images(List.of("img1.jpg", "img2.jpg"))
//                .build();
//
//        ChapterResponse response = ChapterResponse.builder()
//                .id("abc123")
//                .chapter("1")
//                .title("Chapter One")
//                .images(List.of("img1.jpg", "img2.jpg"))
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        when(chapterService.createChapter(any(ChapterRequest.class))).thenReturn(response);
//
//        mockMvc.perform(post("/chapters")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("Chapter One"));
//    }
//
//    @Test
//    public void testGetChapterById() throws Exception {
//        ChapterResponse response = ChapterResponse.builder()
//                .id("abc123")
//                .chapter("1")
//                .title("Chapter One")
//                .images(List.of("img1.jpg"))
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        when(chapterService.getChapterById("abc123")).thenReturn(response);
//
//        mockMvc.perform(get("/chapters/abc123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.chapter").value("1"));
//    }
//
//
//
//    @Test
//    public void testUpdateChapter() throws Exception {
//        ChapterRequest request = ChapterRequest.builder()
//                .chapter("2")
//                .title("Chapter Two")
//                .images(List.of("img2.jpg"))
//                .build();
//
//        ChapterResponse response = ChapterResponse.builder()
//                .id("abc123")
//                .chapter("2")
//                .title("Chapter Two")
//                .images(List.of("img2.jpg"))
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        when(chapterService.updateChapter(eq("abc123"), any(ChapterRequest.class))).thenReturn(response);
//
//        mockMvc.perform(put("/chapters/abc123")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("Chapter Two"));
//    }
//
//    @Test
//    public void testDeleteChapter() throws Exception {
//        doNothing().when(chapterService).deleteChapter("abc123");
//
//        mockMvc.perform(delete("/chapters/abc123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Chapter deleted"));
//    }
//
//}
