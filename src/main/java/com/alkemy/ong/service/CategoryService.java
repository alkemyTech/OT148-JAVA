package com.alkemy.ong.service;

import com.alkemy.ong.domain.Category;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.model.CategoryModel;
import javax.transaction.Transactional;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryDTO createCategory(Category category) {
        CategoryModel categoryModel = CategoryMapper.mapDomainToModel(category);
        CategoryModel save = categoryRepository.save(categoryModel);
        Category categoryDomain = CategoryMapper.mapModelToDomain(save);
        return CategoryMapper.mapDomainToDTO(categoryDomain);
    }
}
