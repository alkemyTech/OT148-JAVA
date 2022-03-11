package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialUpdateDTO;
import com.alkemy.ong.exception.TestimonialNotFoundException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @PutMapping("/testimonials/{id}")
    public ResponseEntity<TestimonialDTO> updateTestimonials(@PathVariable Long id, @RequestBody TestimonialUpdateDTO testimonialUpdateDTO) throws TestimonialNotFoundException {
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

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTestimonial(@PathVariable Long id) throws TestimonialNotFoundException {
        testimonialService.deleteTestimonial(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/testimonials/page/{page}")
    public ResponseEntity<?> getPaginated(@PathVariable Integer page) {
        Map<String, Object> response = new HashMap<>();
        String currentContextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        if (!this.testimonialService.getPaginated(page - 1).isEmpty()) {
            response.put("url previus", currentContextPath.concat(String.format("/members/page/%d", page - 1)));
        }

        if (!this.testimonialService.getPaginated(page + 1).isEmpty()) {
            response.put("url next", currentContextPath.concat(String.format("/members/page/%d", page + 1)));
        }

        response.put("ok", this.testimonialService.getPaginated(page));
        return ResponseEntity.ok(response);
    }

}
