package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryCreationDTO;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.CategoryUpdateDTO;
import com.alkemy.ong.dto.PageDTO;
import com.alkemy.ong.exception.CategoryNotFoundException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CategoryController {

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDTO createCategory(@Valid @RequestBody CategoryCreationDTO categoryCreationDTO);

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    PageDTO<CategoryDTO> findAll(@RequestParam(defaultValue = "0") Integer page);

    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDTO getById(@PathVariable Long id) throws CategoryNotFoundException;

    @PutMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateDTO categoryUpdateDTO) throws CategoryNotFoundException;

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteCategory(@PathVariable Long id) throws CategoryNotFoundException;
}
