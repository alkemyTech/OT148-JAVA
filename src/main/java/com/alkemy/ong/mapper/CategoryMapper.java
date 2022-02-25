package com.alkemy.ong.mapper;
import com.alkemy.ong.domain.Category;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.repository.model.CategoryModel;

public class CategoryMapper {

    public static Category mapModelToDomain(CategoryModel categoryModel){
        Category category = Category.builder()
                .id(categoryModel.getId())
                .name(categoryModel.getName())
                .description(categoryModel.getDescription())
                .image(categoryModel.getImage())
                .creationDate(categoryModel.getCreationDate())
                .build();
        return category;
    }

    public static CategoryModel mapDomainToModel(Category category){
        CategoryModel categoryModel = CategoryModel.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .image(category.getImage())
                .creationDate(category.getCreationDate())
                .build();
        return categoryModel;
    }

    public static Category mapDTOToDomain(CategoryDTO dto) {
        Category category = Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .image(dto.getImage())
                .creationDate(dto.getCreationDate())
                .build();
        return category;
    }

    public static CategoryDTO mapDomainToDTO(Category category) {
        CategoryDTO dto = CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .image(category.getImage())
                .creationDate(category.getCreationDate())
                .build();
        return dto;
    }
}
