package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.SlidesModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlidesRepository extends CrudRepository<SlidesModel,Long> {
}
