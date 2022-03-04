package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class CommentController {

    private final CommentService commentService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> storeContact(@Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = CommentMapper.mapDtoToDomain(commentDTO);
        CommentDTO commentDTOSaved = CommentMapper.mapDomainToDto(commentService.addComment(comment);
        return ResponseEntity.ok(commentDTOSaved);
    }
}
