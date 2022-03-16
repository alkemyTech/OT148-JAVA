package com.alkemy.ong;

import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.service.TestimonialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class TestimonialControllerFunctionalTest {



    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<TestimonialCreationDTO> jsonAdd;

    @Autowired
    private JacksonTester<TestimonialDTO> jsonGet;

    @MockBean
    TestimonialService testimonialService;

    private TestimonialCreationDTO testimonialCreationDTO;
    private TestimonialDTO testimonialDTO;
    private final Long id = 1L;

    @BeforeEach
    public void init() {

        this.testimonialCreationDTO = new TestimonialCreationDTO();
        this.testimonialCreationDTO.setName("Name");
        this.testimonialCreationDTO.setImage("Name.jpg");
        this.testimonialCreationDTO.setContent("NameContent");

        this.testimonialDTO = new TestimonialDTO();
        this.testimonialDTO.setId(this.id);
        this.testimonialDTO.setName(this.testimonialCreationDTO.getName());
        this.testimonialDTO.setImage(this.testimonialCreationDTO.getImage());
        this.testimonialDTO.setContent(this.testimonialCreationDTO.getContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addNewTestimonials() throws Exception {
        Mockito.when(testimonialService.createTestimonial
                        (TestimonialMapper.mapCreationDTOtoDomain(testimonialCreationDTO)))
                .thenReturn(testimonialDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoAddToJson(this.testimonialCreationDTO));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteTestimonial() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/testimonials/{id}", this.id);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    private String dtoAddToJson(TestimonialCreationDTO dto) throws IOException {
        return this.jsonAdd.write(dto).getJson();
    }
}
