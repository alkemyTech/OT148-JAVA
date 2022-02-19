package com.alkemy.ong.repository;


import com.alkemy.ong.repository.model.ActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

    @Repository
    public interface ActivityRepository extends JpaRepository<ActivityModel, Long> {

        List<ActivityModel> findByIsActiveTrue();

        Optional<ActivityModel> findByName(String name);

        boolean existsByName (String name);
    }

