package com.alkemy.ong.exception;

public class WrongValuesException extends RuntimeException {

    public WrongValuesException() {
        super("User and/or password is incorrect");
    }
}
