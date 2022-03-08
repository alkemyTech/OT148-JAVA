package com.alkemy.ong.service;

import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.model.CommentModel;
import org.springframework.transaction.annotation.Transactional;


public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment addComment(Comment comment) {
        CommentModel commentModel = CommentMapper.mapDomainToModel(comment);
        return CommentMapper.mapModelToDomain(commentRepository.save(commentModel));
    }
}
