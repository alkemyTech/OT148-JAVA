package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.MemberController;
import com.alkemy.ong.domain.Member;
import com.alkemy.ong.dto.MemberCreationDTO;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberListDTO;
import com.alkemy.ong.dto.MemberUpdateDTO;
import com.alkemy.ong.exception.MemberNotFoundException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.util.ContextUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "MemberResource", tags = {"Members"})
@RestController
public class MemberResource implements MemberController {

    private final MemberService memberService;

    public MemberResource(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public MemberListDTO findAll(Integer page) {
        var members = memberService.getAll(page);
        MemberListDTO response = new MemberListDTO(page, members, ContextUtils.currentContextPath());
        return response;
    }

    @Override
    public MemberDTO updateMember(Long id, MemberUpdateDTO memberUpdateDTO) throws MemberNotFoundException {
        Member member = MemberMapper.mapUpdateDTOToDomain(memberUpdateDTO);
        return MemberMapper.mapDomainToDTO(memberService.updateMember(id, member));
    }

    @Override
    public MemberDTO createMember(MemberCreationDTO memberCreationDTO) {
        Member member = MemberMapper.mapCreationDTOToDomain(memberCreationDTO);
        MemberDTO memberDTO = MemberMapper.mapDomainToDTO(memberService.createMember(member));
        return memberDTO;
    }

    @Override
    public void deleteMember(Long id) throws MemberNotFoundException {
        memberService.deleteMember(id);
    }
}
