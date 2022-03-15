package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.CategoryController;
import com.alkemy.ong.domain.Category;
import com.alkemy.ong.dto.CategoryCreationDTO;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.CategoryUpdateDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.PageDTO;
import com.alkemy.ong.exception.CategoryNotFoundException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.service.CategoryService;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @Override
    public PageDTO<CategoryDTO> findAll(Integer page) {
        Page<Category> category = categoryService.findAll(page);
        PageDTO<CategoryDTO> categoryDTOPageDTO = createCategoryPageDto(category);
        return categoryDTOPageDTO;
    }

    private PageDTO<CategoryDTO> createCategoryPageDto(Page<Category> page) {
        PageDTO<CategoryDTO> dto = new PageDTO<>();
        dto.setList(page.getContent().stream().map(CategoryMapper::mapDomainToDTO).collect(Collectors.toList()));
        if (page.hasNext()) {
            dto.setNextPage("/categories?page=" + page.nextPageable().getPageNumber());
        }
        if (page.hasPrevious()) {
            dto.setPreviousPage("/categories?page=" + page.previousPageable().getPageNumber());
        }
        dto.setTotalPages(page.getTotalPages());
        return dto;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCategoryNotFoundExceptions(CategoryNotFoundException ex) {
        ErrorDTO categoryNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(categoryNotFound, HttpStatus.NOT_FOUND);
    }
}
