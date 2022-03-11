package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.CommentModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<CommentModel, Long> {

    List<CommentModel> findAllByOrderByCreationDateAsc();
}
