package com.alkemy.ong.service;

import com.alkemy.ong.repository.CommentRepository;

public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
