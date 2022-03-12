package com.alkemy.ong.controller;

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
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/categories")
    public ResponseEntity<?> findAllPage(Pageable pageable) {
        int page = pageable.getPageNumber();
        final int size = 10;
        List<CategoryDTO> list = categoryService.findAllPage(PageRequest.of(page, size)).stream().map(CategoryMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        Page<CategoryDTO> pages = new PageImpl<CategoryDTO>(list, pageable, list.size());
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()
                            + "/categories/?page=0");
        }
        if (page >= 0 && (pages.getNumberOfElements() != 0)) {
            return ResponseEntity.ok().body(pages);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()
                        + "/categories/?page=" + pages.previousPageable().getPageNumber());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) throws CategoryNotFoundException {
        return ResponseEntity.ok(CategoryMapper.mapDomainToDTO(categoryService.getById(id)));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateDTO categoryUpdateDTO) throws CategoryNotFoundException {
        Category category = CategoryMapper.mapUpdateDTOToDomain(categoryUpdateDTO);
        CategoryDTO categoryDTO = CategoryMapper.mapDomainToDTO(categoryService.updateCategory(id, category));
        return ResponseEntity.ok(categoryDTO);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCategoryNotFoundExceptions(CategoryNotFoundException ex) {
        ErrorDTO categoryNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(categoryNotFound, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.deleteCategory(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
