package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Slide {
    private Long id;
    private String image;
    private String text;
    private Integer organizationOrder;
    private Organization organization;
}
