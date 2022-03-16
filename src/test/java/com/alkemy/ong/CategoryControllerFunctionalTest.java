package com.alkemy.ong;

import com.alkemy.ong.dto.CategoryDTO;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
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
import test.java.com.alkemy.ong.util.HeaderBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CategoryControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String categoryControllerUrl;
    private HttpEntity<?> entity;

    @BeforeEach
    void setUp() {
        categoryControllerUrl = testRestTemplate.getRootUri() + "/categories";
    }

    @Test
    void testGetCategories_shouldReturnErrorDTO() {
        String endpointUrl = categoryControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        ResponseEntity<CategoryDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        Assertions.assertEquals(200, response.getStatusCode().value());
    }


}
