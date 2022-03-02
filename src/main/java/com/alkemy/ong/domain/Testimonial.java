package com.alkemy.ong.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Testimonial {
    private Long id;
    private String name;
    private String image;
    private String content;
    private LocalDateTime creationDate;
}
