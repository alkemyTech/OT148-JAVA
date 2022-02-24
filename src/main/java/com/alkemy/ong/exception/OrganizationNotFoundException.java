package com.alkemy.ong.exception;

import javassist.NotFoundException;

public class OrganizationNotFoundException extends NotFoundException {
    public OrganizationNotFoundException(String msg) {
        super(msg);
    }
}
