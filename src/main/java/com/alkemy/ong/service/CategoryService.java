package com.alkemy.ong.service;

import com.alkemy.ong.domain.Category;
import com.alkemy.ong.exception.CategoryNotFoundException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.model.CategoryModel;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Integer PAGE_SIZE = 10;

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
    public Page<Category> findAll(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<CategoryModel> categoryModelPage = categoryRepository.findAll(pageable);
        Page<Category> categoryPage = categoryModelPage.map(CategoryMapper::mapModelToDomain);
        return categoryPage;
    }

    @Transactional
    public Category getById(Long id) throws CategoryNotFoundException {
        Optional<CategoryModel> modelOptional = categoryRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            CategoryModel categoryModel = modelOptional.get();
            return CategoryMapper.mapModelToDomain(categoryModel);
        } else {
            throw new CategoryNotFoundException(String.format("Category with ID: %s not found", id));
        }
    }

    @Transactional
    public Category updateCategory(Long id, Category category) throws CategoryNotFoundException {
        Optional<CategoryModel> optionalCategoryModel = categoryRepository.findById(id);
        if (optionalCategoryModel.isEmpty()) {
            throw new CategoryNotFoundException(String.format("Category with ID: %s not found", id));
        }
        CategoryModel categoryModel = optionalCategoryModel.get();
        categoryModel.setName(category.getName());
        categoryModel.setDescription(category.getDescription());
        categoryModel.setImage(category.getImage());
        categoryModel.setCreationDate(category.getCreationDate());
        return CategoryMapper.mapModelToDomain(categoryRepository.save(categoryModel));
    }

    public void deleteCategory(Long id) throws CategoryNotFoundException {
        Optional<CategoryModel> categoryOptional = categoryRepository.findById(id);
        if (!categoryOptional.isEmpty()) {
            CategoryModel categoryModel = categoryOptional.get();
            categoryRepository.delete(categoryModel);
        } else {
            throw new CategoryNotFoundException(String.format("Category with ID: " + id + " not found", id));
        }
    }
}
