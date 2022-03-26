package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentCreationDTO;
import com.alkemy.ong.dto.CommentDTO;
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

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Comments", description = "Operations related to Comments")
public interface CommentController {

    @Operation(
            summary = "Get comments list",
            description = "To get a paginated list of the ONG comments, you must access this endpoint.")
    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    List<CommentBodyDTO> findAll();

    @Operation(
            summary = "Create new comment",
            description = "To create a comment, you must access this endpoint.")
    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    CommentDTO createComment(@Valid @RequestBody CommentCreationDTO commentCreationDTO);

    @Operation(summary = "Delete a comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a comment by id"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)})
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteComment(Long id);

    @Operation(summary = "Update a comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update a comment by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)})
    @PutMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    CommentDTO updateComment(@PathVariable Long id,
                             @RequestBody CommentBodyDTO commentBodyDTO);

    @Operation(
            summary = "Get comments list by News id",
            description = "To get a list of the ONG comments, filtering by news id, you must access this endpoint.")
    @GetMapping("/posts/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    List<CommentDTO> findAllByPostId(@PathVariable Long id);
}
