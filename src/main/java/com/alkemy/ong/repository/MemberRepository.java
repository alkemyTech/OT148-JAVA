package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.MemberModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<MemberModel, Long> {
}

