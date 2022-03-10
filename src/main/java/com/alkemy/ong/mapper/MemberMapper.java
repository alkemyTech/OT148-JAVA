package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.dto.MemberCreationDTO;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberUpdateDTO;
import com.alkemy.ong.repository.model.MemberModel;

public class MemberMapper {

    public static Member mapModelToDomain(MemberModel memberModel) {
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

    public static MemberModel mapDomainToModel(Member memberDomain) {
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

    public static MemberDTO mapDomainToDTO(Member memberDomain) {
        MemberDTO memberDTO = MemberDTO.builder()
                .id(memberDomain.getId())
                .name(memberDomain.getName())
                .facebookUrl(memberDomain.getFacebookUrl())
                .instagramUrl(memberDomain.getInstagramUrl())
                .linkedinUrl(memberDomain.getLinkedinUrl())
                .image(memberDomain.getImage())
                .description(memberDomain.getDescription())
                .build();
        return memberDTO;
    }

    public static Member mapCreationDTOToDomain(MemberCreationDTO memberCreationDTO) {
        Member member = Member.builder()
                .name(memberCreationDTO.getName())
                .facebookUrl(memberCreationDTO.getFacebookUrl())
                .instagramUrl(memberCreationDTO.getInstagramUrl())
                .linkedinUrl(memberCreationDTO.getLinkedinUrl())
                .image(memberCreationDTO.getImage())
                .description(memberCreationDTO.getDescription())
                .build();
        return member;
    }

    public static Member mapUpdateDTOToDomain(MemberUpdateDTO memberUpdateDTO) {
        Member member = Member.builder()
                .name(memberUpdateDTO.getName())
                .facebookUrl(memberUpdateDTO.getFacebookUrl())
                .instagramUrl(memberUpdateDTO.getInstagramUrl())
                .linkedinUrl(memberUpdateDTO.getLinkedinUrl())
                .image(memberUpdateDTO.getImage())
                .description(memberUpdateDTO.getDescription())
                .build();
        return member;
    }
}
