package com.alkemy.ong;

import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.dto.UserLoginDTO;
import com.alkemy.ong.dto.UserUpdateDTO;
import com.alkemy.ong.util.HeaderBuilder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;

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

    @Test
    void testFindAllUsers() {
        String endpointUrl = testRestTemplate.getRootUri() + "/users";
        HttpHeaders headers = new HeaderBuilder().withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        ResponseEntity<List<UserDTO>> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(200, response.getStatusCode().value());
        assertEquals(20, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testUpdateUser_shouldReturnForbidden() {
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("photo", null);
        parameters.add("user", this.createUserUpdateDTO());

        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String endpointUrl = testRestTemplate.getRootUri() + "/users/{id}";
        HttpEntity<LinkedMultiValueMap<String, Object>> entity =
                new HttpEntity(parameters, null);
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PATCH,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );
        assertEquals(403, response.getStatusCode().value());
    }

    @Test
    void testUpdateUser_shouldReturnNotFound() {
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("photo", null);
        parameters.add("user", this.createUserUpdateDTO());

        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String endpointUrl = testRestTemplate.getRootUri() + "/users/{id}";
        HttpEntity<LinkedMultiValueMap<String, Object>> entity =
                new HttpEntity(parameters, headers);
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PATCH,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "89")
        );
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testUpdateUser_shouldReturnOk() {
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("photo", null);
        parameters.add("user", this.createUserUpdateDTO());

        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String endpointUrl = testRestTemplate.getRootUri() + "/users/{id}";
        HttpEntity<LinkedMultiValueMap<String, Object>> entity =
                new HttpEntity(parameters, headers);
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PATCH,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "2")
        );
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testDeleteUser_shouldReturnErrorDto() {
        String endpointUrl = testRestTemplate.getRootUri() + "/users/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "22")
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }

    @Test
    void testDeleteUser_shouldReturnOk() {
        String endpointUrl = testRestTemplate.getRootUri() + "/users/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );
        assertEquals(200, response.getStatusCode().value());
    }

    private UserUpdateDTO createUserUpdateDTO() {
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .firstName("Pablini")
                .lastName("Diaz")
                .email("pablo@gmail.com")
                .password("40651209")
                .build();
        return userUpdateDTO;
    }
}
