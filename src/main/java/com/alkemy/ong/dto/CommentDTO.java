package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDTO implements Serializable {
    private Long id;
    private Long user_id;
    private String body;
    private Long news_id;
    private LocalDateTime creationDate;

}
