package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.repository.model.MemberModel;

public class MemberMapper {

    public static Member mapMemberDtoToMember(MemberDto memberDto){
            Member newMember = new Member();
            newMember.setName(memberDto.getName().toLowerCase());
            newMember.setFacebookUrl(memberDto.getFacebookUrl());
            newMember.setInstagramUrl(memberDto.getInstagramUrl());
            newMember.setLinkedinUrl(memberDto.getLinkedinUrl());
            newMember.setImage(memberDto.getImage());
            newMember.setDescription(memberDto.getDescription());
            return newMember;
    }

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

    public static MemberDto mapMemberToMemberDto(Member member){
        MemberDto newMemberDto = new MemberDto();
        newMemberDto.setName(member.getName());
        newMemberDto.setFacebookUrl(member.getFacebookUrl());
        newMemberDto.setInstagramUrl(member.getInstagramUrl());
        newMemberDto.setLinkedinUrl(member.getLinkedinUrl());
        newMemberDto.setImage(member.getImage());
        newMemberDto.setDescription(member.getDescription());
        return newMemberDto;
    }

    public static MemberDto mapMemberModelToMemberDto(MemberModel memberModel){
        MemberDto newMemberDto = new MemberDto();
        newMemberDto.setName(memberModel.getName());
        newMemberDto.setFacebookUrl(memberModel.getFacebookUrl());
        newMemberDto.setInstagramUrl(memberModel.getInstagramUrl());
        newMemberDto.setLinkedinUrl(memberModel.getLinkedinUrl());
        newMemberDto.setImage(memberModel.getImage());
        newMemberDto.setDescription(memberModel.getDescription());
        return newMemberDto;
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

    public static MemberModel mapMemberDtoToMemberModel(MemberDto memberDto){
        MemberModel newMemberModel = new MemberModel();
        newMemberModel.setName(memberDto.getName());
        newMemberModel.setFacebookUrl(memberDto.getFacebookUrl());
        newMemberModel.setInstagramUrl(memberDto.getInstagramUrl());
        newMemberModel.setLinkedinUrl(memberDto.getLinkedinUrl());
        newMemberModel.setImage(memberDto.getImage());
        newMemberModel.setDescription(memberDto.getDescription());
        return newMemberModel;
    }
}
