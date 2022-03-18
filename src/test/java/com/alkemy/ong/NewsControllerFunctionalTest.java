package com.alkemy.ong;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.repository.NewsRepository;
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
    private NewsRepository newsRepository;

    @BeforeEach
    void setUp() {
        newsControllerUrl = testRestTemplate.getRootUri() + "/news";
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
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity, new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        //Then
        assertEquals(200, response.getStatusCode().value());
    }
}
