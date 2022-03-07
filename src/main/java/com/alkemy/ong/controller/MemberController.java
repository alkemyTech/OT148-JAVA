package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Member;
import com.alkemy.ong.dto.MemberCreationDTO;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAll() {
        List<MemberDTO> memberDTOS = memberService.getAll()
                .stream().map(MemberMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberDTOS);
    }

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberCreationDTO memberCreationDTO) {
        Member member = MemberMapper.mapCreationDTOToDomain(memberCreationDTO);
        MemberDTO memberDTO = MemberMapper.mapDomainToDTO(memberService.createMember(member));
        return ResponseEntity.status(HttpStatus.CREATED).body(memberDTO);
    }

}
