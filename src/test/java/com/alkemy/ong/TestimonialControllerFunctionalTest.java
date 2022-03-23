package com.alkemy.ong;


import com.alkemy.ong.dto.ErrorDTO;

import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialListDTO;
import com.alkemy.ong.dto.TestimonialUpdateDTO;

import com.alkemy.ong.repository.TestimonialRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class TestimonialControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private TestimonialRepository testimonialRepository;
    private String testimonialControllerUrl;
    private HttpEntity<?> entity;

    @BeforeEach
    void setUp() {
        testimonialControllerUrl = testRestTemplate.getRootUri() + "/testimonials";
    }

    @AfterEach
    void deleteAll() {
        testimonialRepository.deleteAll();
    }

    @Test
    void testCreateTestimonial_shouldReturnCreated() {
        TestimonialCreationDTO testimonialCreationDTO = createTestimonialCreationDTO();
        String endpointUrl = testimonialControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(testimonialCreationDTO, headers);
        ResponseEntity<TestimonialDTO> response = testRestTemplate.exchange(
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
    void testCreateTestimonial_shouldReturnErrorDTO() {
        TestimonialCreationDTO testimonialDTO = TestimonialCreationDTO.builder()
                .name("")
                .image("")
                .content("")
                .build();
        String endpointUrl = testimonialControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(testimonialDTO, headers);
        ResponseEntity<TestimonialDTO> response = testRestTemplate.exchange(
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
    void testDeleteTestimonial_shouldReturnOkResponse() {
        Long id = withCreatedTestimonial();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        String endpointUrl = testimonialControllerUrl + "/{id}";
        // When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.DELETE,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", id)
        );
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testDeleteTestimonial_shouldReturnErrorDto() {
        Long id = withCreatedTestimonial();
        String endpointUrl = testimonialControllerUrl + "/{id}";
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
                Map.of("id", 2)
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }

    @Test
    void testUpdateTestimonial_shouldReturnOkResponse() {
        TestimonialCreationDTO testimonialCreationDTO = createTestimonialCreationDTO();
        Long id = withCreatedTestimonial();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        TestimonialUpdateDTO testimonialUpdateDTO = TestimonialUpdateDTO.builder()
                .name("Santiago")
                .image("santiago.jpg")
                .content("este es mi testimonio")
                .build();
        entity = new HttpEntity(testimonialUpdateDTO, headers);
        String endpointUrl = testimonialControllerUrl + "/{id}";
        // When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", id)
        );
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdateTestimonial_shouldReturnNotFoundException() {
        TestimonialUpdateDTO testimonialUpdateDTO = TestimonialUpdateDTO.builder()
                .name("Santiago")
                .image("santiago.jpg")
                .content("este es el testimonio de Santiago")
                .build();
        String endpointUrl = testimonialControllerUrl + "/{id}";

        entity = new HttpEntity(testimonialUpdateDTO, null);
        // When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );
        assertEquals(403, response.getStatusCode().value());
    }


    @Test
    void testGetTestimonials_ShouldReturnResponseOk() {
        //Given
        String endpointUrl = testimonialControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        //When
        ResponseEntity<TestimonialListDTO> response = testRestTemplate.exchange(
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
    void testGetTestimonials_shouldReturnErrorDTO() {
        String endpointUrl = testimonialControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("user1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        ResponseEntity<TestimonialListDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        assertEquals(403, response.getStatusCode().value());
    }

    private TestimonialCreationDTO createTestimonialCreationDTO() {
        return TestimonialCreationDTO.builder()
                .name("Sebastian")
                .image("sebastian.jpa")
                .content("mi testimonio")
                .build();
    }

    private Long withCreatedTestimonial() {
        TestimonialCreationDTO testimonialCreationDTO = createTestimonialCreationDTO();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(testimonialCreationDTO, headers);
        ResponseEntity<TestimonialDTO> response = testRestTemplate.exchange(
                testimonialControllerUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        return response.getBody().getId();
    }
}
