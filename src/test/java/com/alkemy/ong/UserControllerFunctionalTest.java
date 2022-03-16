package com.alkemy.ong;

import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.util.HeaderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String userControllerUrl;
    private HttpEntity<?> entity;

    @BeforeEach
    void setUp() {
        userControllerUrl = testRestTemplate.getRootUri() + "/auth";
    }

    @Test
    void testRegister_returnsResponseWithJwt() {
        // Given
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .name("santi")
                .lastName("santi")
                .email("email@gmail.com")
                .password("password")
                .build();
        String endpointUrl = userControllerUrl + "/register";
        entity = new HttpEntity(userCreationDTO, null);

        // When
        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        // Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetAllUsers() {
        // Given
        String endpointUrl = testRestTemplate.getRootUri() + "/users";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        // When
        ResponseEntity<List<UserDTO>> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(20, Objects.requireNonNull(response.getBody()).size());
    }
}
