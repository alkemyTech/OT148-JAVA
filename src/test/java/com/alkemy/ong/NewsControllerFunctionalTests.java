package com.alkemy.ong;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.NewsCreationDTO;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.util.HeaderBuilder;
import java.util.Map;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class NewsControllerFunctionalTests {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String newsControllerUrl;
    private HttpEntity<?> entity;

    @BeforeEach
    void setUp() {
        newsControllerUrl = testRestTemplate.getRootUri() + "/news";
    }

    @Test
    void testGetAllNews() {
        String endpointUrl = newsControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        //When
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
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
    void testGetNewsById() {
        String endpointUrl = newsControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        //When
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testCreateNews() {
        NewsCreationDTO newsCreationDTO = NewsCreationDTO.builder()
                .name("novedad1")
                .content("Contenido de novedad1")
                .image("novedad1.jpg")
                .categoryId(1L)
                .build();
        String endpointUrl = newsControllerUrl;
        entity = new HttpEntity(newsCreationDTO, null);

        //When
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
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
    void testUpdateNews() {
        NewsUpdateDTO newsUpdateDTO = NewsUpdateDTO.builder()
                .name("Nueva novedad")
                .content("Contenido de nueva novedad")
                .image("nueva_novedad.jpg")
                .category(CategoryDTO.builder().id(1L).build())
                .build();
        String endpointUrl = newsControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(newsUpdateDTO, headers);

        //When
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testDeleteNews() {
        String endpointUrl = newsControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        //When
        ResponseEntity<?> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetNewsById_shouldReturnErrorDTO() {
        String endpointUrl = newsControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }

    @Test
    void testCreateNews_ShouldReturnErrorDTO() {
        NewsCreationDTO newsCreationDTO = NewsCreationDTO.builder()
                .name("novedad1")
                .content("Contenido de novedad1")
                .image("novedad1.jpg")
                .categoryId(1L)
                .build();
        String endpointUrl = newsControllerUrl;
        entity = new HttpEntity(newsCreationDTO, null);

        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }

    @Test
    void testUpdateNews_shouldReturnErrorDTO() {
        NewsUpdateDTO newsUpdateDTO = NewsUpdateDTO.builder()
                .name("Nueva novedad")
                .content("Contenido de nueva novedad")
                .image("nueva_novedad.jpg")
                .category(CategoryDTO.builder().id(1L).build())
                .build();
        String endpointUrl = newsControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(newsUpdateDTO, headers);

        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }

    @Test
    void testDeleteNews_shouldReturnErrorDTO() {
        String endpointUrl = newsControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }
}
