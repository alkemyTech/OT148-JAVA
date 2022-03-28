package com.alkemy.ong.controller;

import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.SlideCreationDTO;
import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideUpdateDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.exception.SlideNotFoundException;
import java.util.List;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Slides", description = "Operations related to Slides")
public interface SlideController {

    @Operation(
            summary = "Get a Slide by Id",
            description = "To get a Slide by Id , you must access this endpoint"
    )
    @ApiResponse(responseCode = "200",
            description = "Get a Slide by Id",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation =SlideDTO.class))
            })
    @GetMapping("/slides/{id}")
    @ResponseStatus(HttpStatus.OK)
    SlideDTO getById(@PathVariable("id") Long id) throws SlideNotFoundException;

    @Operation(summary = "Delete a Slide by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Slide by id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Slide not found",
                    content = @Content)})
    @DeleteMapping("/slides/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSlide(@PathVariable("id") Long id) throws SlideNotFoundException;

    @Operation(summary = "Update Slide by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Slide by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SlideDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Slide not found",
                    content = @Content)})
    @PutMapping("/slides/{id}")
    @ResponseStatus(HttpStatus.OK)
    SlideDTO updateSlide(@PathVariable Long id, @RequestBody SlideUpdateDTO slideUpdateDTO) throws SlideNotFoundException;


    @Operation(
            summary = "Create a new Slide",
            description = "To create, this endpoint must be accessed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Register Slide",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SlideDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "The fields must not be empty",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Organization Id provided was not found",
                    content = @Content)
    })
    @PostMapping("/slides")
    @ResponseStatus(HttpStatus.CREATED)
    SlideDTO createSlide(@Valid @RequestBody SlideCreationDTO slideCreationDTO);

    @Operation(
            summary = "Find all slides",
            description = "To fetch all slides, you must access this endpoint"
    )
    @ApiResponse(responseCode = "200",
            description = "Find all slides",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            })
    @GetMapping("/slides")
    @ResponseStatus(HttpStatus.OK)
    List<SlideDTO> findAll();
}