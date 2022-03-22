package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryCreationDTO;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.CategoryUpdateDTO;
import com.alkemy.ong.dto.PageDTO;
import com.alkemy.ong.exception.OngRequestException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Tag(name = "Categories", description = "Create, update show and delete Categories")
public interface CategoryController {

    @Operation(
            summary = "Add new category",
            description = "To create a category, you must access this endpoint.")
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDTO createCategory(@Valid @RequestBody CategoryCreationDTO categoryCreationDTO);


    @Operation(
            summary = "Get all categories",
            description = "To get a paginated list of categories you must access this endpoint"

    )
    @ApiResponse(responseCode = "200",
            description = "Get all categories",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageDTO.class))
            })
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    PageDTO<CategoryDTO> findAll(@RequestParam(defaultValue = "0") Integer page);

    @Operation(
            summary = "Get a category by Id",
            description = "To get a category by its Id you must access this endpoint"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get a category by Id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDTO getById(@PathVariable Long id) throws OngRequestException;

    @Operation(summary = "Update a Category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update a category by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)})
    @PutMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateDTO categoryUpdateDTO) throws OngRequestException;

    @Operation(summary = "Delete a Category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a category by id"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)})
    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteCategory(@PathVariable Long id) throws OngRequestException;

}

