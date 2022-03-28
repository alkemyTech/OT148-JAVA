package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.CategoryController;
import com.alkemy.ong.domain.Category;
import com.alkemy.ong.dto.CategoryCreationDTO;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.dto.CategoryUpdateDTO;
import com.alkemy.ong.dto.PageDTO;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Api(value = "CategoryResource", tags = {"Categories"})
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
    public CategoryDTO getById(Long id) throws OngRequestException {
        return CategoryMapper.mapDomainToDTO(categoryService.getById(id));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryUpdateDTO categoryUpdateDTO) throws OngRequestException {
        Category category = CategoryMapper.mapUpdateDTOToDomain(categoryUpdateDTO);
        CategoryDTO categoryDTO = CategoryMapper.mapDomainToDTO(categoryService.updateCategory(id, category));
        return categoryDTO;
    }

    @Override
    public void deleteCategory(Long id) throws OngRequestException {
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
}
