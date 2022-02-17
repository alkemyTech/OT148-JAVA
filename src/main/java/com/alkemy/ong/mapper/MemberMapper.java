package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.repository.model.MemberModel;

public class MemberMapper {

    public static Member mapMemberModelToMember(MemberModel memberModel){
        Member newMember = new Member();
        newMember.setName(memberModel.getName());
        newMember.setFacebookUrl(memberModel.getFacebookUrl());
        newMember.setInstagramUrl(memberModel.getInstagramUrl());
        newMember.setLinkedinUrl(memberModel.getLinkedinUrl());
        newMember.setImage(memberModel.getImage());
        newMember.setDescription(memberModel.getDescription());
        return newMember;
    }

    public static MemberModel mapMemberToMemberModel(Member member){
        MemberModel newMemberModel = new MemberModel();
        newMemberModel.setName(member.getName());
        newMemberModel.setFacebookUrl(member.getFacebookUrl());
        newMemberModel.setInstagramUrl(member.getInstagramUrl());
        newMemberModel.setLinkedinUrl(member.getLinkedinUrl());
        newMemberModel.setImage(member.getImage());
        newMemberModel.setDescription(member.getDescription());
        return newMemberModel;
    }
}
