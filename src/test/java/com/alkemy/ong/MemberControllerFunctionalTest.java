package com.alkemy.ong;

import com.alkemy.ong.dto.ErrorDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class MemberControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String memberControllerUrl;
    private HttpEntity<?> entity;

    @BeforeEach
    void setUp() {
        memberControllerUrl = testRestTemplate.getRootUri() + "/members";
    }

    @Test
    void testUpdateMember_shouldReturnErrorDto() {
        String endpointUrl = memberControllerUrl + "/{id}";
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
                Map.of("id", "1")
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getCode());
    }
}
