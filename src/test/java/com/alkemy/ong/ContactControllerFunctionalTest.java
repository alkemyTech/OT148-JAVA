package com.alkemy.ong;


import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.repository.ContactRepository;
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
public class ContactControllerFunctionalTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ContactRepository contactRepository;
    private String contactControllerUrl;
    private HttpEntity<?> entity;

    @BeforeEach
    void setUp() {
        contactControllerUrl = testRestTemplate.getRootUri() + "/contacts";
    }

    @AfterEach
    void deleteAll() {
        contactRepository.deleteAll();
    }

    @Test
    void testCreateContact_shouldReturnCreated() {
        ContactDTO contactDTO = createContactDto();
        String endpointUrl = contactControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(contactDTO, headers);
        ResponseEntity<ContactDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        assertEquals(201, response.getStatusCode().value());
    }

    private ContactDTO createContactDto() {
        return ContactDTO.builder()
                .name("Sebastian")
                .phone(261883992)
                .email("sebas@gmail.com")
                .message("Gracias por todo ")
                .build();
    }

    @Test
    void testCreateContact_shouldReturnErrorDTO() {
        ContactDTO contactDTO = ContactDTO.builder()
                .name("")
                .phone(0)
                .message("")
                .email("")
                .build();
        String endpointUrl = contactControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(contactDTO, headers);
        ResponseEntity<ContactDTO> response = testRestTemplate.exchange(
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
    void testGetContacts_shouldReturnOkResponse() {
        ContactDTO contactDTO = createContactDto();
        String endpointUrl = contactControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(contactDTO, headers);
        ResponseEntity<ContactDTO> response = testRestTemplate.exchange(
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
    void testGetContacts_shouldReturnErrorDTO() {
        ContactDTO contactDTO = createContactDto();
        String endpointUrl = contactControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("user1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(contactDTO, headers);
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        assertEquals(403, response.getStatusCode().value());
    }

}
