package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberCreationDTO;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberListDTO;
import com.alkemy.ong.dto.MemberUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Members", description = "Create, update show and delete Members")
@RequestMapping("/members")
public interface MemberController {

    @Operation(
            summary = "Get members list",
            description = "To get a paginated list of the ONG members, you must access this endpoint.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    MemberListDTO findAll(@RequestParam(defaultValue = "0") Integer page);

    @Operation(summary = "Update a Member by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update a member by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MemberDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content)})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    MemberDTO updateMember(@PathVariable Long id, @RequestBody MemberUpdateDTO memberUpdateDTO);

    @Operation(
            summary = "Add new member",
            description = "To create a member, you must access this endpoint.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MemberDTO createMember(@Valid @RequestBody MemberCreationDTO memberCreationDTO);

    @Operation(summary = "Delete a Member by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a member by id"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteMember(@PathVariable Long id);
}
