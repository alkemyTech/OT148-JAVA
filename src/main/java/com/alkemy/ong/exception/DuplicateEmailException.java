package com.alkemy.ong.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String msg) {
        super(msg);
    }
}
