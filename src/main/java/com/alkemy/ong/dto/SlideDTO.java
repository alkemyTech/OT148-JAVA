package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlideDTO {
    private String image;
    private String text;
    private Integer order;
}
