package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Activity {
    private Long id;
    private String name;
    private String content;
    private String image;
    private LocalDateTime creationDate;
}
