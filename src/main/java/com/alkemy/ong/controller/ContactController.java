package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDTO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ContactController {

    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    ContactDTO createContact(@Valid @RequestBody ContactDTO contactDTO);

    @GetMapping("/contacts")
    List<ContactDTO> findAll();

}
