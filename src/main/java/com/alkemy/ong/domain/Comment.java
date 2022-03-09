package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {
    private Long id;
    private Long userId;
    private String body;
    private Long newsId;
    private LocalDateTime creationDate;
}
