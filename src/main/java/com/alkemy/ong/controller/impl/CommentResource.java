package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.CommentController;
import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.exception.CommentNotFoundException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentResource implements CommentController {

    private final CommentService commentService;

    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public List<CommentBodyDTO> findAll() {
        return commentService.findAll()
                .stream()
                .map(CommentMapper::mapDomainToBodyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(@PathVariable Long id) throws CommentNotFoundException {
        commentService.deleteComment(id);
    }
}
