package com.alkemy.ong.exception;

public class ParamNotFoundException extends RuntimeException {
    public ParamNotFoundException(String message) {
        super(message);
    }
}
