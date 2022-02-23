package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserModel,Long> {

    List<UserModel> findAll();

    UserModel findByEmail (String email);

    boolean existsByEmail (String email);

}
