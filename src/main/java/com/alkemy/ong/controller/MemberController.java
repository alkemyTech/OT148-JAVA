package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberCreationDTO;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberListDTO;
import com.alkemy.ong.dto.MemberUpdateDTO;
import com.alkemy.ong.exception.MemberNotFoundException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface MemberController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    MemberListDTO findAll(@RequestParam(defaultValue = "0") Integer page);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    MemberDTO updateMember(@PathVariable Long id, @RequestBody MemberUpdateDTO memberUpdateDTO) throws MemberNotFoundException;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MemberDTO createMember(@Valid @RequestBody MemberCreationDTO memberCreationDTO);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteMember(@PathVariable Long id) throws MemberNotFoundException;

}
