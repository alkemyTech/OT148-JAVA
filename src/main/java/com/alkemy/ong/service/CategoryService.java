package com.alkemy.ong.service;

import com.alkemy.ong.domain.Category;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.exception.CategoryNotFoundException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.model.CategoryModel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createCategory(Category category) {
        CategoryModel categoryModel = CategoryMapper.mapDomainToModel(category);
        CategoryModel save = categoryRepository.save(categoryModel);
        return CategoryMapper.mapModelToDomain(save);
    }

    @Transactional
    public List<CategoryDTO> getAll() {
        List<CategoryModel> categoryModelList = categoryRepository.findAll();
        return categoryModelList.stream().map(CategoryMapper::mapModelToDomain)
                .map(CategoryMapper::mapDomainToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Category getById(Long id) throws CategoryNotFoundException {
        Optional<CategoryModel> modelOptional = categoryRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            CategoryModel categoryModel = modelOptional.get();
            return CategoryMapper.mapModelToDomain(categoryModel);
        } else {
            throw new CategoryNotFoundException(String.format("Categories with ID: %s not found", id));
        }
    }
}
