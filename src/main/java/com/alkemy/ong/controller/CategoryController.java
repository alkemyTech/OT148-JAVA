package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Category;
import com.alkemy.ong.dto.CategoryCreationDTO;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.service.CategoryService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryCreationDTO categoryCreationDTO) {
        Category categoryDomain = CategoryMapper.mapCreationDTOToDomain(categoryCreationDTO);
        CategoryDTO categoryDTO = CategoryMapper.mapDomainToDTO(categoryService.createCategory(categoryDomain));
        return ResponseEntity.ok(categoryDTO);
    }
}
