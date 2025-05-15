//package org.uploadservice.controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.multipart.MultipartFile;
//import org.uploadservice.controller.UploadController;
//import org.uploadservice.dto.common.ApiResponse;
//import org.uploadservice.dto.request.FromUrlUploadRequest;
//import org.uploadservice.dto.response.FromUrlUploadResponse;
//import org.uploadservice.service.UploadService;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class UploadControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private UploadService uploadService;
//
//    @InjectMocks
//    private UploadController uploadController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(uploadController).build();
//    }
//
//    @Test
//    void testUploadImage() throws Exception {
//        MultipartFile file = mock(MultipartFile.class);
//        String expectedUrl = "https://example.com/image.jpg";
//        when(uploadService.uploadImage(file)).thenReturn(expectedUrl);
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
//                        .multipart("/upload/image")
//                        .file("file", "test image content".getBytes())
//                        .contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value(expectedUrl))
//                .andExpect(jsonPath("$.message").value("Image uploaded successfully"))
//                .andExpect(jsonPath("$.status").value(200))
//                .andReturn();
//
//        verify(uploadService).uploadImage(file);
//    }
//
//
//}
//
