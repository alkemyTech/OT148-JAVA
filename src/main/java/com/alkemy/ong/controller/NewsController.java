package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.NewsListDTO;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.exception.NewsNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "News", description = "Operations for News")
public interface NewsController {

    @Operation(
            summary = "Get all news",
            description = "To get a paginated list of news you must access this endpoint"

    )
    @ApiResponse(responseCode = "200",
            description = "Get all news",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewsListDTO.class))
            })
    @GetMapping("/news")
    ResponseEntity<NewsListDTO> getAll(@RequestParam(defaultValue = "0") Integer page);

    @Operation(
            summary = "Get news by Id",
            description = "To get news by Id you must access this endpoint"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get news by Id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NewsDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "New not found", content = @Content)
    })
    @GetMapping("/news/{id}")
    ResponseEntity<NewsDTO> getById(@PathVariable Long id) throws NewsNotFoundException;

    @Operation(
            summary = "Create news",
            description = "To create news you must access this endpoint"

    )
    @ApiResponse(responseCode = "201",
            description = "Create news",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewsDTO.class))
            })
    @PostMapping("/news")
    ResponseEntity<NewsDTO> createNews(@Valid @RequestBody NewsDTO newsDTO);

    @Operation(
            summary = "Update news by Id",
            description = "To update news by Id you must access this endpoint"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update news by Id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NewsDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "New not found", content = @Content)
    })
    @PutMapping("/news/{id}")
    ResponseEntity<NewsDTO> updateNews(@PathVariable Long id,
                                       @RequestBody NewsUpdateDTO newsUpdateDTO) throws NewsNotFoundException;

    @Operation(
            summary = "Delete news by Id",
            description = "To delete news by Id you must access this endpoint"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete news by Id"),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "New not found", content = @Content)
    })
    @DeleteMapping("news/{id}")
    ResponseEntity<?> deleteNews(@PathVariable Long id) throws NewsNotFoundException;
}
