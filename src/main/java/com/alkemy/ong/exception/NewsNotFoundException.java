package com.alkemy.ong.exception;

import javassist.NotFoundException;

public class NewsNotFoundException extends NotFoundException {

    public NewsNotFoundException(String msg) {
        super(msg);
    }
}
