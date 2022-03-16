package com.alkemy.ong.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(String msg) {
        super(msg);
    }
}
