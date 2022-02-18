package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Category;
import com.alkemy.ong.repository.model.CategoryModel;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category categoryModel2Category (CategoryModel categoryModel){
        Category category= Category.builder().id(categoryModel.getId()).
                name(categoryModel.getName()).
                description(categoryModel.getDescription()).
                image(categoryModel.getImage()).
                creationDate(categoryModel.getCreationDate()).build();
        return category;
    }

    public CategoryModel category2CategoryModel(Category category){
        CategoryModel categoryModel= CategoryModel.builder().
                id(category.getId()).
                name(category.getName()).
                description(category.getDescription()).
                image(category.getImage()).
                creationDate(category.getCreationDate()).build();
        return categoryModel;
    }
}
