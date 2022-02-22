package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.TestimonialModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends CrudRepository<TestimonialModel, Long> {
}

