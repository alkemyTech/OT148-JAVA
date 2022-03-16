package com.alkemy.ong.controller.impl;


import com.alkemy.ong.controller.ContactController;
import com.alkemy.ong.domain.Contact;
import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.service.ContactService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.alkemy.ong.mapper.ContactMapper.mapDomainToDto;
import static com.alkemy.ong.mapper.ContactMapper.mapDtoToDomain;

@RestController
public class ContactResource implements ContactController {

    private final ContactService contactService;

    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = mapDtoToDomain(contactDTO);
        ContactDTO contactDTOSave = mapDomainToDto(contactService.addContact(contact));
        return contactDTOSave;
    }

    @Override
    public List<ContactDTO> findAll() {
        List<ContactDTO> contactDTOS = contactService.getAll()
                .stream().map(ContactMapper::mapDomainToDto)
                .collect(Collectors.toList());
        return contactDTOS;
    }
}