package com.alkemy.ong;

import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.MemberCreationDTO;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberUpdateDTO;
import com.alkemy.ong.repository.MemberRepository;
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
class MemberControllerFunctionalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private MemberRepository memberRepository;
    private String memberControllerUrl;
    private HttpEntity<?> entity;

    public MemberCreationDTO createMemberDto() {
        return MemberCreationDTO.builder()
                .name("Daniel")
                .facebookUrl("facebookUrl")
                .instagramUrl("instagramUrl")
                .linkedinUrl("linkedinUrl")
                .image("imageDaniel")
                .description("description")
                .build();
    }

    private Long withCreatedMember() {
        MemberCreationDTO memberCreationDTO = createMemberDto();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(memberCreationDTO, headers);
        ResponseEntity<MemberDTO> response = testRestTemplate.exchange(
                memberControllerUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                },
                Map.of()
        );
        return response.getBody().getId();
    }

    @BeforeEach
    void setUp() {
        memberControllerUrl = testRestTemplate.getRootUri() + "/members";
    }

    @AfterEach
    void deleteAll() {
        memberRepository.deleteAll();
    }

    @Test
    void testDeleteMember_shouldReturnErrorDto() {
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

    @Test
    void testDeleteMember_shouldReturnOkResponse() {
        MemberCreationDTO memberCreationDTO = createMemberDto();
        Long id = withCreatedMember();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(memberCreationDTO, headers);
        String endpointUrl = memberControllerUrl + "/{id}";
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
    void testCreateMember_shouldReturnCreated() {
        MemberCreationDTO memberCreationDTO = createMemberDto();
        String endpointUrl = memberControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(memberCreationDTO, headers);
        ResponseEntity<MemberDTO> response = testRestTemplate.exchange(
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
    void testCreateMember_shouldReturnErrorDTO() {
        MemberCreationDTO memberDTO = MemberCreationDTO.builder()
                .name("")
                .facebookUrl("facebookUrl")
                .instagramUrl("instagramUrl")
                .linkedinUrl("linkedinUrl")
                .image("imageDaniel")
                .description("description")
                .build();
        String endpointUrl = memberControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(memberDTO, headers);
        ResponseEntity<MemberDTO> response = testRestTemplate.exchange(
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
    void testUpdateMember_shouldReturnErrorDto() {
        MemberUpdateDTO memberUpdateDTO = MemberUpdateDTO.builder()
                .name("Daniel")
                .facebookUrl("facebookUrl")
                .instagramUrl("instagramUrl")
                .linkedinUrl("linkedinUrl")
                .image("imageDaniel")
                .description("description")
                .build();
        String endpointUrl = memberControllerUrl + "/{id}";

        entity = new HttpEntity(memberUpdateDTO, null);
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
    void testUpdateMember_shouldReturnOkResponse() {
        MemberCreationDTO memberCreationDTO = createMemberDto();
        Long id = withCreatedMember();
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        MemberUpdateDTO memberUpdateDTO = MemberUpdateDTO.builder()
                .name("Daniel2")
                .facebookUrl("facebookUrl")
                .instagramUrl("instagramUrl")
                .linkedinUrl("linkedinUrl")
                .image("imageDaniel")
                .description("description")
                .build();
        entity = new HttpEntity(memberUpdateDTO, headers);
        String endpointUrl = memberControllerUrl + "/{id}";
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
    void testGetMembers_shouldReturnOkResponse() {
        MemberCreationDTO memberCreationDTO = createMemberDto();
        String endpointUrl = memberControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("admin1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(memberCreationDTO, headers);
        ResponseEntity<MemberDTO> response = testRestTemplate.exchange(
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
    void testGetMembers_shouldReturnErrorDTO() {
        MemberCreationDTO memberCreationDTO = createMemberDto();
        String endpointUrl = memberControllerUrl;
        HttpHeaders headers = new HeaderBuilder()
                .withValidToken("user1@gmail.com", 3600L)
                .build();
        entity = new HttpEntity(memberCreationDTO, headers);
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
