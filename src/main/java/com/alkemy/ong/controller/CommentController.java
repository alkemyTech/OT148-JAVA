package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentBodyDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CommentController {

    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    List<CommentBodyDTO> findAll();
}
