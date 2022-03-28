package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityCreationDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.ActivityUpdateDTO;
import com.alkemy.ong.exception.OngRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Tag(name = "Activities", description = "Operations related to Activities")
public interface ActivityController {

    @Operation(
            summary = "Create a new Activity",
            description = "To created an activity, this endpoint must be accessed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Created activity",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "The fields must not be empty",
                    content = @Content)
    })
    @PostMapping("/activities")
    @ResponseStatus(HttpStatus.CREATED)
    ActivityDTO createActivity(@Valid @RequestBody ActivityCreationDTO activityCreationDTO);

    @Operation(summary = "Update Activity by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Activity by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActivityDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Activity not found",
                    content = @Content)})
    @PutMapping("/activities/{id}")
    @ResponseStatus(HttpStatus.OK)
    ActivityDTO updateActivity(@PathVariable Long id, @RequestBody ActivityUpdateDTO activityUpdateDTO) throws OngRequestException;

}
