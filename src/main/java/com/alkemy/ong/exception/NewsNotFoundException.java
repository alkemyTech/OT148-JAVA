package com.alkemy.ong.exception;

import javassist.NotFoundException;

public class NewsNotFoundException extends RuntimeException {

    public NewsNotFoundException(String msg) {
        super(msg);
    }
}
