package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentBodyDTO {
    private String body;
}