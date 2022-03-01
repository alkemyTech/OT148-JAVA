package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.NewsModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends CrudRepository<NewsModel, Long> {
}
