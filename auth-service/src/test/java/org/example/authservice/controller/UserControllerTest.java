package org.example.authservice.controller;

import org.example.authservice.dto.request.UserCreateRequest;
import org.example.authservice.dto.response.UserResponse;
import org.example.authservice.service.UserService;
import org.example.authservice.testConfig.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
/*@Import(TestConfig.class)*/
@ActiveProfiles("test")
class UserControllerTest {
    /*@Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userCreateRequest = UserCreateRequest.builder()
                .username("username")
                .password("password")
                .email("email")
                .build();
        userResponse = UserResponse.builder()
                .username("username")
                .password("password")
                .email("email")
                .build();
    }

    @Test
    void createUser() throws Exception {
        // GIVEN
        //setUp()


        // WHEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreateRequest);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/identity/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userResponse.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userResponse.getEmail()));

        // THEN

    }

}

// Given
// When
// Then
*/
}