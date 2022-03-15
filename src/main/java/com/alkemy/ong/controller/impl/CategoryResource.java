package com.alkemy.ong.controller.impl;


import com.alkemy.ong.controller.CategoryController;
import com.alkemy.ong.domain.Category;
import com.alkemy.ong.dto.CategoryCreationDTO;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.CategoryUpdateDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.exception.CategoryNotFoundException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryResource implements CategoryController {

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDTO createCategory(CategoryCreationDTO categoryCreationDTO) {
        Category categoryDomain = CategoryMapper.mapCreationDTOToDomain(categoryCreationDTO);
        CategoryDTO categoryDTO = CategoryMapper.mapDomainToDTO(categoryService.createCategory(categoryDomain));
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<CategoryDTO> categoryDTOS = categoryService.getAll()
                .stream().map(CategoryMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return categoryDTOS;
    }

    @Override
    public CategoryDTO getById(Long id) throws CategoryNotFoundException {
        return CategoryMapper.mapDomainToDTO(categoryService.getById(id));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryUpdateDTO categoryUpdateDTO) throws CategoryNotFoundException {
        Category category = CategoryMapper.mapUpdateDTOToDomain(categoryUpdateDTO);
        CategoryDTO categoryDTO = CategoryMapper.mapDomainToDTO(categoryService.updateCategory(id, category));
        return categoryDTO;
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        categoryService.deleteCategory(id);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCategoryNotFoundExceptions(CategoryNotFoundException ex) {
        ErrorDTO categoryNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(categoryNotFound, HttpStatus.NOT_FOUND);
    }

}
