package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel,Long> {
}
