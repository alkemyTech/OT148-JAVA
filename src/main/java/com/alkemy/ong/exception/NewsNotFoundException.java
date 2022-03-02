package com.alkemy.ong.exception;

public class NewsNotFoundException extends RuntimeException {

    public NewsNotFoundException(String msg) {
        super(msg);
    }
}
