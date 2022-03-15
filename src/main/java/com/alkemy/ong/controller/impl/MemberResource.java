package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.MemberController;
import com.alkemy.ong.domain.Member;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.MemberCreationDTO;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberListDTO;
import com.alkemy.ong.dto.MemberUpdateDTO;
import com.alkemy.ong.exception.MemberNotFoundException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.util.ContextUtils;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "MemberResource", tags = {"Members"})
@RestController
public class MemberResource implements MemberController {

    private final MemberService memberService;

    public MemberResource(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public ResponseEntity<MemberListDTO> getAll(Integer page) {
        var members = memberService.getAll(page);
        MemberListDTO response = new MemberListDTO(page, members, ContextUtils.currentContextPath());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MemberDTO> updateMember(Long id, MemberUpdateDTO memberUpdateDTO) throws MemberNotFoundException {
        Member member = MemberMapper.mapUpdateDTOToDomain(memberUpdateDTO);
        return ResponseEntity.ok(MemberMapper.mapDomainToDTO(memberService.updateMember(id, member)));
    }

    @Override
    public ResponseEntity<MemberDTO> createMember(MemberCreationDTO memberCreationDTO) {
        Member member = MemberMapper.mapCreationDTOToDomain(memberCreationDTO);
        MemberDTO memberDTO = MemberMapper.mapDomainToDTO(memberService.createMember(member));
        return ResponseEntity.status(HttpStatus.CREATED).body(memberDTO);
    }

    @Override
    public ResponseEntity<?> deleteMember(Long id) throws MemberNotFoundException {
        memberService.deleteMember(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleMemberNotFoundExceptions(MemberNotFoundException ex) {
        ErrorDTO memberNotFound = ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).build();
        return new ResponseEntity(memberNotFound, HttpStatus.NOT_FOUND);
    }
}
