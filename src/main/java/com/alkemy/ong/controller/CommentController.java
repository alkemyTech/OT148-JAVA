package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentCreationDTO;
import com.alkemy.ong.dto.CommentDTO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CommentController {

    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    List<CommentBodyDTO> findAll();

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    CommentDTO createComment(@Valid @RequestBody CommentCreationDTO commentCreationDTO);
}
