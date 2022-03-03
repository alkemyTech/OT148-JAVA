package com.alkemy.ong.service;

import com.alkemy.ong.domain.Contact;
import static com.alkemy.ong.mapper.ContactMapper.mapDomainToModel;
import static com.alkemy.ong.mapper.ContactMapper.mapModelToDomain;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.repository.model.ContactModel;
import org.springframework.transaction.annotation.Transactional;


public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public Contact addContact(Contact contact) {
        ContactModel contactModel = mapDomainToModel(contact);
        ContactModel save = contactRepository.save(contactModel);
        return mapModelToDomain(save);
    }
}
