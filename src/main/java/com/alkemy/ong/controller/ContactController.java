package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Contacts", description = "Operations related to Contacts")
public interface ContactController {

    @Operation(
            summary = "Create contact",
            description = "To create contact you must access this endpoint"

    )
    @ApiResponse(responseCode = "201",
            description = "Create contact",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDTO.class))
            })
    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    ContactDTO createContact(@Valid @RequestBody ContactDTO contactDTO);

    @Operation(
            summary = "Get all contacts",
            description = "To get a list of contacts you must access this endpoint"

    )
    @ApiResponse(responseCode = "200",
            description = "Get all contacts")
    @GetMapping("/contacts")
    List<ContactDTO> findAll();

}
