package com.alkemy.ong;

import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentCreationDTO;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.exception.ApiErrorDTO;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.model.CategoryModel;
import com.alkemy.ong.repository.model.NewsModel;
import com.alkemy.ong.util.HeaderBuilder;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CommentControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String commentControllerUrl;
    private HttpEntity<?> entity;
    private Long newsId;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        commentControllerUrl = testRestTemplate.getRootUri();
        CategoryModel categoryModel = CategoryModel.builder().name("Categoria1").description("Descripcion de categoria1").image("categoria1.jpg").build();
        categoryRepository.save(categoryModel);
        NewsModel newsModel = NewsModel.builder().name("Novedad1").content("Contenido de novedad1").image("novedad1.jpg").categoryModel(categoryModel).build();
        newsRepository.save(newsModel);
        newsId = newsModel.getId();
    }

    @AfterEach
    void deleteAll() {
        commentRepository.deleteAll();
    }

    @Test
    void testGetComments_ShouldReturnResponseOk() {
        //Given
        String endpointUrl = commentControllerUrl + "/comments";
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
    void testGetCommentsByPostId_ShouldReturnResponseOk() {
        //Given
        String endpointUrl = commentControllerUrl + "/posts/{id}/comments";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        // When
        ResponseEntity<?> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", newsId)
        );

        //Then
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetCommentsByPostId_whenIdDoesNotExist_shouldReturnNotFound() {
        //Given
        String endpointUrl = commentControllerUrl + "/posts/{id}/comments";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        // When
        ResponseEntity<ApiErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "500")
        );

        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testDeleteCommentsById_ShouldReturnResponseOk() {
        //Given
        String endpointUrl = commentControllerUrl + "/comments/{id}";
        Long id = createdComment();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        // When
        ResponseEntity<CommentDTO> response = testRestTemplate.exchange(
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
    void testDeleteCommentsById_whenIdDoesNotExist_shouldReturnNotFound() {
        //Given
        String endpointUrl = commentControllerUrl + "/comments/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);

        // When
        ResponseEntity<ApiErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "500")
        );

        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testCreateComments_ShouldReturnResponseCreated() {
        //Given
        CommentCreationDTO commentCreationDTO = CommentCreationDTO.builder()
                .body("Cuerpo del commentario")
                .newsId(newsId)
                .build();
        String endpointUrl = commentControllerUrl + "/comments";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(commentCreationDTO, headers);

        //When
        ResponseEntity<CommentDTO> response = testRestTemplate.exchange(
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
    void testCreateComments_whenBodyIsBlank_shouldReturnBadRequest() {
        //Given
        CommentCreationDTO commentCreationDTO = CommentCreationDTO.builder()
                .body("")
                .newsId(newsId)
                .build();
        String endpointUrl = commentControllerUrl + "/comments";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(commentCreationDTO, headers);

        //When
        ResponseEntity<ApiErrorDTO> response = testRestTemplate.exchange(
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
    void testUpdateCommentsById_ShouldReturnResponseOk() {
        //Given
        String endpointUrl = commentControllerUrl + "/comments/{id}";
        Long id = createdComment();
        CommentBodyDTO commentBodyDTO = CommentBodyDTO.builder()
                .body("Nuevo cuerpo")
                .build();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(commentBodyDTO, headers);

        //When
        ResponseEntity<CommentDTO> response = testRestTemplate.exchange(
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
    void testUpdateComment_whenIdDoesNotExist_shouldReturnNotFound() {
        //Given
        CommentBodyDTO commentBodyDTO = CommentBodyDTO.builder()
                .body("Nuevo cuerpo")
                .build();
        String endpointUrl = commentControllerUrl + "/comments/{id}";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(commentBodyDTO, headers);

        //When
        ResponseEntity<ApiErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "500")
        );

        //Then
        assertEquals(404, response.getStatusCode().value());
    }

    private Long createdComment() {
        //Given
        CommentCreationDTO commentCreationDTO = CommentCreationDTO.builder()
                .body("Cuerpo del comentario")
                .newsId(newsId)
                .build();
        String endpointUrl = commentControllerUrl + "/comments";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(commentCreationDTO, headers);

        //When
        ResponseEntity<CommentDTO> response = testRestTemplate.exchange(
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
