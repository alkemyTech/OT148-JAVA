package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.TestimonialController;
import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialListDTO;
import com.alkemy.ong.dto.TestimonialUpdateDTO;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.service.TestimonialService;
import com.alkemy.ong.util.ContextUtils;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "testimonialResource", tags = {"Testimonials"})
@RestController
public class TestimonialResource implements TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialResource(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @Override
    public TestimonialDTO createTestimonial(TestimonialCreationDTO testimonialCreationDTO) {
        Testimonial testimonial = TestimonialMapper.mapCreationDTOtoDomain(testimonialCreationDTO);
        TestimonialDTO testimonialDTO = TestimonialMapper.mapDomainToDTO(testimonialService.createTestimonial(testimonial));
        return testimonialDTO;
    }

    @Override
    public TestimonialDTO updateTestimonial(Long id, TestimonialUpdateDTO testimonialUpdateDTO) throws OngRequestException {
        Testimonial testimonial =
                TestimonialMapper.mapUpdateDTOToDomain(testimonialUpdateDTO);
        TestimonialDTO testimonialDTO = TestimonialMapper.mapDomainToDTO(testimonialService.updateTestimonial(id, testimonial));
        return testimonialDTO;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TestimonialNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleTestimonialNotFound(TestimonialNotFoundException ex) {
        ErrorDTO testimonialNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(testimonialNotFound, HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteTestimonial(Long id) throws OngRequestException {
        testimonialService.deleteTestimonial(id);
    }

    @Override
    public TestimonialListDTO getAll(Integer page) {
        var testimonials = testimonialService.getAll(page);
        TestimonialListDTO response = new TestimonialListDTO(page, testimonials, ContextUtils.currentContextPath());
        return response;
    }
}
