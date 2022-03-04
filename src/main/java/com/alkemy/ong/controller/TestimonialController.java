package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestimonialController {
    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @PostMapping("/testimonials")
    public ResponseEntity<TestimonialDTO> createTestimonial(@Valid @RequestBody TestimonialCreationDTO testimonialCreationDTO) {
        Testimonial testimonial = TestimonialMapper.mapCreationDTOtoDomain(testimonialCreationDTO);
        testimonialService.createTestimonial(testimonial);
        TestimonialDTO testimonialDTO = TestimonialMapper.mapDomainToDTO(testimonial);
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialDTO);
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
}
