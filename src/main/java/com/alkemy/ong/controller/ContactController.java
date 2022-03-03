package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDTO;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contacts")
    public ResponseEntity<ContactDTO> storeContact(@Valid @RequestBody ContactDTO contactDTO) {
        return null;
    }
}
