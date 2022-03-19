package com.alkemy.ong;

import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.util.HeaderBuilder;
import java.util.List;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class OngControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String ongControllerUrl;
    private HttpEntity<?> entity;

    @Autowired
    OrganizationRepository organizationRepository;

    @BeforeEach
    void setUp() {
        ongControllerUrl = testRestTemplate.getRootUri() + "/organization";
    }

    @AfterEach
    void deleteAll() {
        organizationRepository.deleteAll();
    }

    @Test
    void testGetOrganization_shouldReturnOrganizationDTOList() {
        //Given
        String endpointUrl = ongControllerUrl + "/public";
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(null, headers);
        //When
        ResponseEntity<List<OrganizationDTO>> response = testRestTemplate.exchange(
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
    void testUpdateOrganization_shouldReturnForbidden() {
        //Given
        OrganizationUpdateDTO organizationUpdateDTO = OrganizationUpdateDTO.builder()
                .name("nahu")
                .address("asd")
                .phone(123)
                .email("nahu@gmail.com")
                .welcomeText("welcome")
                .aboutUsText("about")
                .facebookUrl("fb")
                .instagramUrl("ig")
                .linkedinUrl("ln")
                .build();
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("image", null);
        parameters.add("organization", organizationUpdateDTO);
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String endpointUrl = ongControllerUrl + "/{id}";
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity(parameters, null);
        //When
        ResponseEntity<OrganizationDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PATCH,
                httpEntity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1")
        );
        //Then
        assertEquals(403, response.getStatusCode().value());
    }

    @Test
    void testUpdateOrganization_shouldReturnErrorDTO() {
        //Given
        OrganizationUpdateDTO organizationUpdateDTO = OrganizationUpdateDTO.builder()
                .name("nahu")
                .address("asd")
                .phone(123)
                .email("nahu@gmail.com")
                .welcomeText("welcome")
                .aboutUsText("about")
                .facebookUrl("fb")
                .instagramUrl("ig")
                .linkedinUrl("ln")
                .build();
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("image", null);
        parameters.add("organization", organizationUpdateDTO);
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String endpointUrl = ongControllerUrl + "/{id}";
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity(parameters, headers);
        //When
        ResponseEntity<ErrorDTO> response = testRestTemplate.exchange(
                endpointUrl,
                HttpMethod.PATCH,
                httpEntity,
                new ParameterizedTypeReference<>() {
                },
                Map.of("id", "1000")
        );
        //Then
        assertEquals(404, response.getStatusCode().value());
    }

}
