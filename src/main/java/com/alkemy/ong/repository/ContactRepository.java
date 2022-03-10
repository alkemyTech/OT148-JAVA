package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.ContactModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<ContactModel, Long> {

    List<ContactModel> findAll();
}
