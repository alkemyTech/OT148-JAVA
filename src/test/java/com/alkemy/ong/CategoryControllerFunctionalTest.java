package com.alkemy.ong;

import com.alkemy.ong.dto.CategoryCreationDTO;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.CategoryUpdateDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.PageDTO;
import com.alkemy.ong.repository.CategoryRepository;
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
public class CategoryControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String categoryControllerUrl;
    private HttpEntity<?> entity;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryControllerUrl = testRestTemplate.getRootUri() + "/categories";
    }

    @AfterEach
    void deleteAll() {
        categoryRepository.deleteAll();
    }

    @Test
    void testGetCategories_shouldReturnPageDTO() {
        //Given
        String endpointUrl = categoryControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        //When
        ResponseEntity<PageDTO<CategoryDTO>> response = testRestTemplate.exchange(
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
    void testGetCategoryById_shouldReturnCategoryDTO() {
        //Given
        String endpointUrl = categoryControllerUrl + "/{id}";
        Long id = withCreatedCategory();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        // When
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(
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
    void testGetCategoryById_shouldReturnErrorDTO() {
        //Given
        String endpointUrl = categoryControllerUrl + "/{id}";
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
                Map.of("id", "10")
        );
        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testDeleteCategoryById_shouldReturnOkResponse() {
        //given
        String endpointUrl = categoryControllerUrl + "/{id}";
        Long id = withCreatedCategory();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        // When
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(
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
    void testDeleteCategoryById_shouldReturnErrorDto() {
        //Given
        String endpointUrl = categoryControllerUrl + "/{id}";
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
                Map.of("id", "100")
        );
        //Then
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }

    @Test
    void testCreateCategory_shouldReturnCategoryDTO() {
        //Given
        CategoryCreationDTO categoryCreationDTO = CategoryCreationDTO.builder()
                .name("Nahuel")
                .image("nahuel.img")
                .description("Nahuel Description")
                .build();
        String endpointUrl = categoryControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(categoryCreationDTO, headers);
        //When
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(
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
    void testCreateCategory_shouldReturnErrorDto() {
        //Given
        CategoryCreationDTO categoryCreationDTO = CategoryCreationDTO.builder()
                .name("")
                .image("nahuel.img")
                .description("Nahuel Description")
                .build();
        String endpointUrl = categoryControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(categoryCreationDTO, headers);
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
    void testUpdateCategory_shouldReturnCategoryDTO() {
        //Given
        Long id = withCreatedCategory();
        CategoryUpdateDTO categoryUpdateDTO = CategoryUpdateDTO.builder()
                .name("Walter")
                .image("Walter.img")
                .description("Walter Description")
                .build();
        String endpointUrl = categoryControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(categoryUpdateDTO, headers);
        //When
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(
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
    void testUpdateCategory_shouldReturnErrorDTO() {
        //Given
        CategoryUpdateDTO categoryUpdateDTO = CategoryUpdateDTO.builder()
                .name("Walter")
                .image("Walter.img")
                .description("Walter Description")
                .build();
        String endpointUrl = categoryControllerUrl + "/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(categoryUpdateDTO, headers);
        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "100")
        );
        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    private Long withCreatedCategory() {
        //Given
        CategoryCreationDTO categoryCreationDTO = CategoryCreationDTO.builder()
                .name("Nahuel")
                .image("nahuel.img")
                .description("Nahuel Description")
                .build();
        String endpointUrl = categoryControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(categoryCreationDTO, headers);
        //When
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(
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
