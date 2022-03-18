package com.alkemy.ong;

import com.alkemy.ong.dto.ActivityCreationDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.ActivityUpdateDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.repository.ActivityRepository;
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
class ActivityControllerFunctionalTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ActivityRepository activityRepository;
    private String activityControllerUrl;
    private HttpEntity<?> entity;

    @BeforeEach
    void setUp() {
        activityControllerUrl = testRestTemplate.getRootUri() + "/activities";
    }

    @AfterEach
    void deleteAll() {
        activityRepository.deleteAll();
    }

    @Test
    void testCreateActivity_shouldReturnCreated() {
        ActivityCreationDTO activityCreationDTO = createActivityDto();
        String endpointUrl = activityControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(activityCreationDTO, headers);
        ResponseEntity<ActivityDTO> response = testRestTemplate.exchange(
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
    void testCreateActivity_shouldReturnErrorDTO() {
        ActivityCreationDTO activityCreationDTO = ActivityCreationDTO.builder()
                .name("")
                .image("imageDaniel")
                .content("content")
                .build();
        String endpointUrl = activityControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(activityCreationDTO, headers);
        ResponseEntity<ActivityDTO> response = testRestTemplate.exchange(
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
    void testUpdateActivity_shouldReturnErrorDto() {
        ActivityUpdateDTO activityUpdateDTO = ActivityUpdateDTO.builder()
                .name("Daniel")
                .image("imageDaniel")
                .content("content")
                .build();
        String endpointUrl = activityControllerUrl + "/{id}";

        entity = new HttpEntity(activityUpdateDTO, null);
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
    void testUpdateActivity_shouldReturnOkResponse() {
        Long id = withCreatedActivity();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        ActivityUpdateDTO activityUpdateDTO = ActivityUpdateDTO.builder()
                .name("Daniel2")
                .image("imageDaniel")
                .content("content")
                .build();
        entity = new HttpEntity(activityUpdateDTO, headers);
        String endpointUrl = activityControllerUrl + "/{id}";
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

    private ActivityCreationDTO createActivityDto() {
        return ActivityCreationDTO.builder()
                .name("Daniel")
                .image("imageDaniel")
                .content("content")
                .build();
    }

    private Long withCreatedActivity() {
        ActivityCreationDTO activityCreationDTO = createActivityDto();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(activityCreationDTO, headers);
        ResponseEntity<ActivityDTO> response = testRestTemplate.exchange(
                activityControllerUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        return response.getBody().getId();
    }
}
