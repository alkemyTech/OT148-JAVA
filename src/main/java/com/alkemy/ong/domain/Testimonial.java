package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Testimonial {
    private String name;
    private String image;
    private String content;
    private LocalDateTime creationDate;
}
