package com.alkemy.ong.exception;

public class OngRequestException extends RuntimeException {
    private final String code;

    public OngRequestException(String message, String code) {
        super(message);
        this.code = code;
    }
}
