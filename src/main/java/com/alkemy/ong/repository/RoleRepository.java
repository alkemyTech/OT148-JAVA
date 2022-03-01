package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.RoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleModel, Long> {

    RoleModel findByName(String name);
}
