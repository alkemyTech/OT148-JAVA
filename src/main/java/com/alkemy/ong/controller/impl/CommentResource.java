package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.CommentController;
import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentCreationDTO;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.exception.CommentNotFoundException;
import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.exception.OperationNotPermitted;
import com.alkemy.ong.mapper.CommentMapper;
import static com.alkemy.ong.mapper.CommentMapper.mapBodyDTOToDomain;
import static com.alkemy.ong.mapper.CommentMapper.mapDomainToDto;
import com.alkemy.ong.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public CommentDTO createComment(CommentCreationDTO commentCreationDTO) {
        Comment comment = commentService
                .createComment(CommentMapper.mapCreationDTOToDomain(commentCreationDTO));
        return mapDomainToDto(comment);
    }

    @ExceptionHandler(NewsNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleNewsNotFound(NewsNotFoundException ex) {
        ErrorDTO newsNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(newsNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleNewsNotFound(CommentNotFoundException ex) {
        ErrorDTO commentNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(commentNotFound, HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteComment(@PathVariable Long id) throws CommentNotFoundException {
        commentService.deleteComment(id);
    }

    @Override
    public CommentDTO updateComment(Long id, CommentBodyDTO commentBodyDTO) {
        return mapDomainToDto(commentService.updateComment(id, mapBodyDTOToDomain(commentBodyDTO)));
    }

    @ExceptionHandler(OperationNotPermitted.class)
    private ResponseEntity<ErrorDTO> handleOpNotPermittedException(OperationNotPermitted ex) {
        ErrorDTO badRequest =
                ErrorDTO.builder()
                        .code(HttpStatus.FORBIDDEN)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(badRequest, HttpStatus.FORBIDDEN);
    }
}
