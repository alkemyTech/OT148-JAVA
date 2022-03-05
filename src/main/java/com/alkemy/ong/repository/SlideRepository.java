package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.SlideModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends CrudRepository<SlideModel, Long> {
    SlideModel findFirstByOrganization_IdOrderByOrganizationOrderDesc(Long id);
}
