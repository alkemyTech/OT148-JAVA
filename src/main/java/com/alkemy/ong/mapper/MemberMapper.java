package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.repository.model.MemberModel;

public class MemberMapper {

    public static Member mapModelToDomain(MemberModel memberModel){
        Member memberDomain = Member.builder()
                .id(memberModel.getId())
                .name(memberModel.getName())
                .facebookUrl(memberModel.getFacebookUrl())
                .instagramUrl(memberModel.getInstagramUrl())
                .linkedinUrl(memberModel.getLinkedinUrl())
                .image(memberModel.getImage())
                .description(memberModel.getDescription()).build();
        return memberDomain;
    }

    public static MemberModel mapDomainToModel(Member memberDomain){
        MemberModel memberModel = MemberModel.builder()
                .id(memberDomain.getId())
                .name(memberDomain.getName())
                .facebookUrl(memberDomain.getFacebookUrl())
                .instagramUrl(memberDomain.getInstagramUrl())
                .linkedinUrl(memberDomain.getLinkedinUrl())
                .image(memberDomain.getImage())
                .description(memberDomain.getDescription()).build();
        return memberModel;
    }
}
