package com.alkemy.ong.service;

import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.exception.CommentNotFoundException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.model.CommentModel;
import java.util.List;
import java.util.Optional;
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
    public List<Comment> findAll() {
        return commentRepository.findAllByOrderByCreationDateAsc()
                .stream()
                .map(CommentMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long id) throws CommentNotFoundException {
        Optional<CommentModel> commentModelOptional = commentRepository.findById(id);
        if (!commentModelOptional.isEmpty()) {
            CommentModel commentModel = commentModelOptional.get();
            commentRepository.delete(commentModel);
        } else {
            throw new CommentNotFoundException(String.format("Comment with ID: %s not found", id));
        }
    }
}
