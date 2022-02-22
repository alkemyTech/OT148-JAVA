package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.OrganizationModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends CrudRepository<OrganizationModel, Long> {
    List<OrganizationModel> findAll();
}
