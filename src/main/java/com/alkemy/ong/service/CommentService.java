package com.alkemy.ong.service;

import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.model.CommentModel;
import java.util.List;
import java.util.stream.Collectors;
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

    @Transactional(readOnly = true)
    public List<Comment> findCommentsByCreationDate() {
        return commentRepository.findAllByOrderByCreationDateAsc()
                .stream()
                .map(CommentMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

}
