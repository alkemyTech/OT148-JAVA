package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.RoleModel;
import com.alkemy.ong.util.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleModel,Long> {

    RoleModel findByName(String name);

}
