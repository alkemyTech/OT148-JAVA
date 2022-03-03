package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Contact;
import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.repository.model.ContactModel;

public class ContactMapper {

    public static Contact mapDtoToDomain(ContactDTO contactDTO) {
        Contact contact = Contact.builder()
                .name(contactDTO.getName())
                .phone(contactDTO.getPhone())
                .email(contactDTO.getEmail())
                .message(contactDTO.getMessage()).build();
        return contact;
    }

    public static ContactModel mapDomainToModel(Contact contact) {
        ContactModel contactModel = ContactModel.builder()
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getMessage()).build();
        return contactModel;
    }

    public static Contact mapModelToDomain(ContactModel contactModel) {
        Contact contact = Contact.builder()
                .name(contactModel.getName())
                .phone(contactModel.getPhone())
                .email(contactModel.getEmail())
                .message(contactModel.getMessage()).build();
        return contact;
    }

    public static ContactDTO mapDomainToDto(Contact contact) {
        ContactDTO contactDTO = ContactDTO.builder()
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getMessage()).build();
        return contactDTO;
    }


}
