package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberCreationDTO;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberListDTO;
import com.alkemy.ong.dto.MemberUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface MemberController {

    @GetMapping
    ResponseEntity<MemberListDTO> getAll(@RequestParam(defaultValue = "0") Integer page);

    @PutMapping("/{id}")
    ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDTO memberUpdateDTO);

    @PostMapping
    ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberCreationDTO memberCreationDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteMember(@PathVariable Long id);
}
