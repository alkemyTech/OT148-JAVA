package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDTO implements Serializable {
    private Long id;
    private Long idUser;
    private String body;
    private Long idNews;
    private LocalDateTime creationDate;

}
