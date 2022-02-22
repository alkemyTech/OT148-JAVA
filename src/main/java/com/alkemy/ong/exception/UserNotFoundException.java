package com.alkemy.ong.exception;

import javassist.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
