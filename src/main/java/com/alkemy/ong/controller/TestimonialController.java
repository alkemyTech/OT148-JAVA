package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialUpdateDTO;
import com.alkemy.ong.exception.TestimonialNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.Map;

public interface TestimonialController {

    @Operation(
            summary = "Add new Testimonials",
            description = )
    @PostMapping("/testimonials")
    ResponseEntity<TestimonialDTO>
    createTestimonial(@Valid @RequestBody TestimonialCreationDTO testimonialCreationDTO);

    @PutMapping("/testimonials/{id}")
    ResponseEntity<TestimonialDTO> updateTestimonials
            (@PathVariable Long id, @RequestBody TestimonialUpdateDTO testimonialUpdateDTO)
            throws TestimonialNotFoundException;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex);

    @DeleteMapping("{id}")
    ResponseEntity<?> deleteTestimonial(@PathVariable Long id)
            throws TestimonialNotFoundException;
}
