package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.SlideModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends CrudRepository<SlideModel, Long> {

    List<SlideModel> findByOrganization_IdOrderByOrganizationOrder(Long id);
}
