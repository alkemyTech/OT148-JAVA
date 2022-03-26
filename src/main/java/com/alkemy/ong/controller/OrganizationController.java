package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Organizations", description = "Operations related to Organizations")
public interface OrganizationController {

    @Operation(
            summary = "Get a list of Organizations",
            description = "To get a list of organizations you must access this endpoint"

    )
    @ApiResponse(responseCode = "200",
            description = "Get all organizations",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrganizationDTO.class))
            })
    @GetMapping("/organization/public")
    @ResponseStatus(HttpStatus.OK)
    List<OrganizationDTO> findAll();


    @Operation(
            summary = "Update Organization by Id",
            description = "To update organizations by Id you must access this endpoint"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update organizations by Id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrganizationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)
    })
    @PatchMapping("/organization/{id}")
    @ResponseStatus(HttpStatus.OK)
    OrganizationDTO updateOrganization(
            @PathVariable Long id,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("organization") OrganizationUpdateDTO organizationUpdateDTO)
            throws OrganizationNotFoundException;
}
