package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.CommentController;
import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentCreationDTO;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.exception.ApiErrorDTO;
import com.alkemy.ong.exception.CommentNotFoundException;
import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.exception.OperationNotPermittedException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.alkemy.ong.mapper.CommentMapper.mapBodyDTOToDomain;
import static com.alkemy.ong.mapper.CommentMapper.mapDomainToDto;

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
    private ResponseEntity<ApiErrorDTO> handleNewsNotFound(NewsNotFoundException ex) {
        ApiErrorDTO newsNotFound =
                ApiErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(newsNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    private ResponseEntity<ApiErrorDTO> handleNewsNotFound(CommentNotFoundException ex) {
        ApiErrorDTO commentNotFound =
                ApiErrorDTO.builder()
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

    @Override
    public List<CommentDTO> findAllByPostId(Long id) {
        List<Comment> commentList = commentService.getAllComment(id);
        return commentList.stream().map(CommentMapper::mapDomainToDto).collect(Collectors.toList());
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    private ResponseEntity<ApiErrorDTO> handleOpNotPermittedException(OperationNotPermittedException ex) {
        ApiErrorDTO badRequest =
                ApiErrorDTO.builder()
                        .code(HttpStatus.FORBIDDEN)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(badRequest, HttpStatus.FORBIDDEN);
    }
}
