package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialCreationDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.dto.TestimonialListDTO;
import com.alkemy.ong.dto.TestimonialUpdateDTO;
import com.alkemy.ong.exception.TestimonialNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Tag(name = "Testimonials", description = "Create, update show and delete Testimonials")
import javax.validation.Valid;
import java.util.Map;

@Tag(name = "Testimonials", description = "Create, update show and delete Testimonials")
public interface TestimonialController {

    @Operation(
            summary = "Add new Testimonial",
            description = "To add a testimonial, you must access this endpoint")
    @PostMapping("/testimonials")
    ResponseEntity<TestimonialDTO> createTestimonial(@Valid @RequestBody TestimonialCreationDTO testimonialCreationDTO);

    @Operation(summary = "Update a Testimonial by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Testimonial by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TestimonialDTO.class))}),

            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Testimonial not found",
                    content = @Content)})
    @PutMapping("/testimonials/{id}")
    ResponseEntity<TestimonialDTO> updateTestimonial
            (@PathVariable Long id, @RequestBody TestimonialUpdateDTO testimonialUpdateDTO)
            throws TestimonialNotFoundException;

    @Operation(summary = "Delete a Testimonial by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Testimonial by id",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Testimonial not found",
                    content = @Content)})
    @DeleteMapping("{id}")
    ResponseEntity<?> deleteTestimonial(@PathVariable Long id)
            throws TestimonialNotFoundException;

    @Operation(
            summary = "Get testimonials list",
            description = "To get a paginated list of the ONG testimonials, you must access this endpoint.")
    @GetMapping
    ResponseEntity<TestimonialListDTO> getAll(@RequestParam(defaultValue = "0") Integer page);
}