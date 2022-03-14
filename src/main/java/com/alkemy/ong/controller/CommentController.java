package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentCreationDTO;
import com.alkemy.ong.dto.CommentDTO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CommentController {

    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    List<CommentBodyDTO> findAll();

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    CommentDTO createComment(@Valid @RequestBody CommentCreationDTO commentCreationDTO);

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteComment(Long id);

    @PutMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    CommentDTO updateComment(@PathVariable Long id,
                             @RequestBody CommentBodyDTO commentBodyDTO);

    @GetMapping("/post/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    List<CommentDTO> findAllComment(@PathVariable Long id);
}
