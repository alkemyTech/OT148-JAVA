package com.alkemy.ong.service;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.repository.model.MemberModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberService {

    private static final int PAGE_SIZE = 10;

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Page<Member> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        var paginatedMembers = memberRepository.findAll(pageable);
        var members = paginatedMembers.getContent().stream().map(MemberMapper::mapModelToDomain).collect(Collectors.toList());
        return new PageImpl<>(members, pageable, paginatedMembers.getTotalElements());
    }

    @Transactional
    public Member updateMember(Long id, Member member) throws OngRequestException {
        Optional<MemberModel> optionalMemberModel = memberRepository.findById(id);
        if (optionalMemberModel.isEmpty()) {
            throw new OngRequestException("Member not found", "not.found");
        }
        MemberModel memberModel = optionalMemberModel.get();
        memberModel.setName(member.getName());
        memberModel.setFacebookUrl(member.getFacebookUrl());
        memberModel.setInstagramUrl(memberModel.getInstagramUrl());
        memberModel.setLinkedinUrl(member.getLinkedinUrl());
        memberModel.setImage(member.getImage());
        memberModel.setDescription(member.getDescription());
        return MemberMapper.mapModelToDomain(memberRepository.save(memberModel));
    }

    @Transactional
    public Member createMember(Member member) {
        MemberModel memberModel = MemberMapper.mapDomainToModel(member);
        return MemberMapper.mapModelToDomain(memberRepository.save(memberModel));
    }

    public void deleteMember(Long id) throws OngRequestException {
        Optional<MemberModel> memberOptional = memberRepository.findById(id);
        if (!memberOptional.isEmpty()) {
            MemberModel memberModel = memberOptional.get();
            memberRepository.delete(memberModel);
        } else {
            throw new OngRequestException("Member not found", "not.found");
        }
    }

}
