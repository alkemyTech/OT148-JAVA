package com.alkemy.ong;

import com.alkemy.ong.dto.CategoryCreationDTO;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.NewsCreationDTO;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.util.HeaderBuilder;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
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
public class NewsControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String newsControllerUrl;
    private HttpEntity<?> entity;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        newsControllerUrl = testRestTemplate.getRootUri() + "/news";
    }

    @BeforeEach
    void createdCategory() {
        CategoryCreationDTO categoryCreationDTO = CategoryCreationDTO.builder()
                .name("Categoria1")
                .description("Esta es la categoria1")
                .image("categoria1.jpg")
                .build();
        categoryService.createCategory(CategoryMapper.mapCreationDTOToDomain(categoryCreationDTO));
    }

    @AfterEach
    void deleteAll() {
        newsRepository.deleteAll();
    }

    @Test
    void testGetNews() {
        //Given
        String endpointUrl = newsControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        //When
        ResponseEntity<?> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetNewsById() {
        //Given
        String endpointUrl = newsControllerUrl + "/{id}";
        Long id = createdNews();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        // When
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", id)
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetNewsById_shouldReturnErrorDTO() {
        //Given
        String endpointUrl = newsControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        // When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "5")
        );
        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testDeleteNewsById() {
        //Given
        String endpointUrl = newsControllerUrl + "/{id}";
        Long id = createdNews();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        // When
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", id)
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testDeleteNewsById_shouldReturnErrorDTO() {
        //Given
        String endpointUrl = newsControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        // When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "5")
        );
        //Then
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }

    @Test
    void testCreateNews() {
        //Given
        NewsCreationDTO newsCreationDTO = NewsCreationDTO.builder()
                .name("Novedad1")
                .content("Contenido de novedad1")
                .image("novedad1.jpg")
                .categoryId(1L)
                .build();
        String endpointUrl = newsControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(newsCreationDTO, headers);
        //When
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void testCreateNews_shouldReturnErrorDto() {
        //Given
        NewsCreationDTO newsCreationDTO = NewsCreationDTO.builder()
                .name("")
                .content("Contenido de novedad1")
                .image("novedad1.jpg")
                .categoryId(1L)
                .build();
        String endpointUrl = newsControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(newsCreationDTO, headers);
        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testUpdateNewsById() {
        //Given
        String endpointUrl = newsControllerUrl + "/{id}";
        Long id = createdNews();
        CategoryDTO categoryDTO = CategoryDTO.builder().id(1L).build();
        NewsUpdateDTO newsUpdateDTO = NewsUpdateDTO.builder()
                .name("Nueva novedad")
                .content("Nuevo contenido")
                .image("nueva_novedad.jpg")
                .category(categoryDTO)
                .build();
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
                Map.of("id", id)
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdateNews_shouldReturnErrorDTO() {
        //Given
        NewsUpdateDTO newsUpdateDTO = NewsUpdateDTO.builder()
                .name("")
                .content("Nuevo contenido")
                .image("nueva_novedad.jpg")
                .category(CategoryDTO.builder().id(10L).build())
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
                Map.of("id", "5")
        );
        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    private Long createdNews() {
        //Given
        NewsCreationDTO newsCreationDTO = NewsCreationDTO.builder()
                .name("Novedad1")
                .content("Contenido de novedad1")
                .image("novedad1.jpg")
                .categoryId(1L)
                .build();
        String endpointUrl = newsControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(newsCreationDTO, headers);
        //When
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(201, response.getStatusCode().value());
        return response.getBody().getId();
    }
}
