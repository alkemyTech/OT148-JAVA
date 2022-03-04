package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {
    private Long id;
    private Long idUser;
    private String body;
    private Long idNews;
    private LocalDateTime creationDate;
}
