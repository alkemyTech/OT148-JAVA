package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.NewsModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsModel, Long> {

    List<NewsModel> findAll();
}
