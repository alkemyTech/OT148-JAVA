package com.alkemy.ong.service;

import com.alkemy.ong.domain.Contact;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.repository.model.ContactModel;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import static com.alkemy.ong.mapper.ContactMapper.mapDomainToModel;
import static com.alkemy.ong.mapper.ContactMapper.mapModelToDomain;


public class ContactService {
    private final ContactRepository contactRepository;
    private final EmailService emailService;

    public ContactService(ContactRepository contactRepository, EmailService emailService) {
        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    @Transactional
    public Contact addContact(Contact contact) {
        ContactModel contactModel = mapDomainToModel(contact);
        emailService.greetingsContact(contactModel.getEmail());
        return mapModelToDomain(contactRepository.save(contactModel));
    }

    @Transactional
    public List<Contact> getAll() {
        List<ContactModel> contactModelList = contactRepository.findAll();
        return contactModelList.stream().map(ContactMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }
}
