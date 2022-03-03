package com.alkemy.ong.service;

import com.alkemy.ong.repository.ContactRepository;

public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
}
