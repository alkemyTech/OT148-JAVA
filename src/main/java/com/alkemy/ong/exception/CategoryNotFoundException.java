package com.alkemy.ong.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
