package com.alkemy.ong.repository;

import com.alkemy.ong.repository.model.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberModel,Integer> {
    public MemberModel findByIdMember(Integer idMember);
    public MemberModel findByName(String name);
}
