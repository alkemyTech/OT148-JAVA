package com.alkemy.ong.service;

import com.alkemy.ong.domain.Category;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;

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
    public Category getById(Long id) throws OngRequestException {
        Optional<CategoryModel> modelOptional = categoryRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            CategoryModel categoryModel = modelOptional.get();
            return CategoryMapper.mapModelToDomain(categoryModel);
        } else {
            throw new OngRequestException("Category not found", "not.found");
        }
    }

    @Transactional
    public Category updateCategory(Long id, Category category) throws OngRequestException {
        Optional<CategoryModel> optionalCategoryModel = categoryRepository.findById(id);
        if (optionalCategoryModel.isEmpty()) {
            throw new OngRequestException("Category not found", "not.found");
        }
        CategoryModel categoryModel = optionalCategoryModel.get();
        categoryModel.setName(category.getName());
        categoryModel.setDescription(category.getDescription());
        categoryModel.setImage(category.getImage());
        categoryModel.setCreationDate(category.getCreationDate());
        return CategoryMapper.mapModelToDomain(categoryRepository.save(categoryModel));
    }

    public void deleteCategory(Long id) throws OngRequestException {
        Optional<CategoryModel> categoryOptional = categoryRepository.findById(id);
        if (!categoryOptional.isEmpty()) {
            CategoryModel categoryModel = categoryOptional.get();
            categoryRepository.delete(categoryModel);
        } else {
            throw new OngRequestException("Category not found", "not.found");
        }
    }
}
