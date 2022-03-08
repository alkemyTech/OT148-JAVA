package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {
    private Long id;
    private Long user_id;
    private String body;
    private Long news_id;
    private LocalDateTime creationDate;
}
