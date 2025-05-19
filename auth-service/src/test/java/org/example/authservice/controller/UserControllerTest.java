package org.example.authservice.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
