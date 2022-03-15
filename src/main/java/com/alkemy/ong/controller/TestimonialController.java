package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialUpdateDTO;
import com.alkemy.ong.exception.TestimonialNotFoundException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface TestimonialController {

    @PostMapping("/testimonials")
    @ResponseStatus(HttpStatus.CREATED)
    TestimonialDTO createTestimonial(@Valid @RequestBody TestimonialCreationDTO testimonialCreationDTO);

    @PutMapping("/testimonials/{id}")
    @ResponseStatus(HttpStatus.OK)
    TestimonialDTO updateTestimonials(@PathVariable Long id,
                                      @RequestBody TestimonialUpdateDTO testimonialUpdateDTO)
            throws TestimonialNotFoundException;

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTestimonial(@PathVariable Long id) throws TestimonialNotFoundException;
    
}
