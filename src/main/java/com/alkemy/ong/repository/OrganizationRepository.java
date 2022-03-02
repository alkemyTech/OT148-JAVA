package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.OrganizationModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<OrganizationModel, Long> {
    List<OrganizationModel> findAll();
}
