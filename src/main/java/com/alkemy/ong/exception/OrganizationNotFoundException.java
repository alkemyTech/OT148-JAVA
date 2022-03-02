package com.alkemy.ong.exception;

public class OrganizationNotFoundException extends RuntimeException {

    public OrganizationNotFoundException(String msg) {
        super(msg);
    }
}
