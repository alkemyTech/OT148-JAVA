package com.alkemy.ong;

import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserLoginDTO;
import com.alkemy.ong.util.HeaderBuilder;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerFuncionalTest {

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
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .name("santi")
                .lastName("santi")
                .email("email@gmail.com")
                .password("password")
                .build();
        String endpointUrl = userControllerUrl + "/register";
        entity = new HttpEntity(userCreationDTO, null);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void testRegister_returnsErrorDto() {
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .name("santi")
                .email("email@gmail.com")
                .password("password")
                .build();
        String endpointUrl = userControllerUrl + "/register";
        entity = new HttpEntity(userCreationDTO, null);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testLogin_returnsResponseWithJwt() {
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("admin1@gmail.com")
                .password("passwordadmin")
                .build();
        String endpointUrl = userControllerUrl + "/login";
        entity = new HttpEntity(userLoginDTO, null);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testLogin_returnsResponseErrorDto() {
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("admin@gmail.com")
                .password("passwordadmin")
                .build();
        String endpointUrl = userControllerUrl + "/login";
        entity = new HttpEntity(userLoginDTO, null);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testAuthMe_returnsResponseOk() {
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("user1@gmail.com", 3600L)
                .build();
        String endpointUrl = userControllerUrl + "/me";
        entity = new HttpEntity(null, headers);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    void testAuthMe_returnsErrorDto() {
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin@gmail.com", 3600L)
                .build();
        String endpointUrl = userControllerUrl + "/me";
        entity = new HttpEntity(null, headers);

        ResponseEntity<JwtDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(403, response.getStatusCode().value());
    }

}
