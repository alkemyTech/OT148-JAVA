package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.CategoryModel;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryModel, Long> {
    List<CategoryModel> findAll(Pageable pageable);
}
