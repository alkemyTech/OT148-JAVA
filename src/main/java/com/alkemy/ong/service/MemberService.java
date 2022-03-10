package com.alkemy.ong.service;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.exception.MemberNotFoundException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.repository.model.MemberModel;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public List<Member> getAll() {
        List<MemberModel> memberModelList = memberRepository.findAll();
        return memberModelList.stream().map(MemberMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public Member updateMember(Long id, Member member) throws MemberNotFoundException {
        Optional<MemberModel> optionalMemberModel = memberRepository.findById(id);
        if (optionalMemberModel.isEmpty()) {
            throw new MemberNotFoundException(String.format("Member with ID: %s not found", id));
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

    public void deleteMember(Long id) throws MemberNotFoundException {
        Optional<MemberModel> memberOptional = memberRepository.findById(id);
        if (!memberOptional.isEmpty()) {
            MemberModel memberModel = memberOptional.get();
            memberRepository.delete(memberModel);
        } else {
            throw new MemberNotFoundException(String.format("Member with ID: %s not found", id));
        }
    }

}
