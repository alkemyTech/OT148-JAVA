package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.ActivityModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends CrudRepository<ActivityModel, Long> {
}
