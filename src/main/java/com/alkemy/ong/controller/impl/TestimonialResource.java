package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.TestimonialController;
import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialUpdateDTO;
import com.alkemy.ong.exception.TestimonialNotFoundException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.service.TestimonialService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api (value= "testimonialResource", tags = {"testimonials"})
@RestController
public class TestimonialResource implements TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialResource(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @Override
    public ResponseEntity<TestimonialDTO> createTestimonial(TestimonialCreationDTO testimonialCreationDTO) {
        Testimonial testimonial = TestimonialMapper.mapCreationDTOtoDomain(testimonialCreationDTO);
        testimonialService.createTestimonial(testimonial);
        TestimonialDTO testimonialDTO = TestimonialMapper.mapDomainToDTO(testimonial);
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialDTO);
    }

    @Override
    public ResponseEntity<TestimonialDTO> updateTestimonial(Long id, TestimonialUpdateDTO testimonialUpdateDTO) throws TestimonialNotFoundException {
        Testimonial testimonial =
                TestimonialMapper.mapUpdateDTOToDomain(testimonialUpdateDTO);
        TestimonialDTO testimonialDTO = TestimonialMapper.mapDomainToDTO(testimonialService.updateTestimonial(id, testimonial));
        return ResponseEntity.ok(testimonialDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @Override
    public ResponseEntity<?> deleteTestimonial(Long id) throws TestimonialNotFoundException {
        testimonialService.deleteTestimonial(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
